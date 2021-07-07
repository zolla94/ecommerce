jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { VenditoreService } from '../service/venditore.service';
import { IVenditore, Venditore } from '../venditore.model';
import { IUserExtra } from 'app/entities/user-extra/user-extra.model';
import { UserExtraService } from 'app/entities/user-extra/service/user-extra.service';

import { VenditoreUpdateComponent } from './venditore-update.component';

describe('Component Tests', () => {
  describe('Venditore Management Update Component', () => {
    let comp: VenditoreUpdateComponent;
    let fixture: ComponentFixture<VenditoreUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let venditoreService: VenditoreService;
    let userExtraService: UserExtraService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [VenditoreUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(VenditoreUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VenditoreUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      venditoreService = TestBed.inject(VenditoreService);
      userExtraService = TestBed.inject(UserExtraService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call userExtra query and add missing value', () => {
        const venditore: IVenditore = { id: 456 };
        const userExtra: IUserExtra = { id: 20144 };
        venditore.userExtra = userExtra;

        const userExtraCollection: IUserExtra[] = [{ id: 32281 }];
        spyOn(userExtraService, 'query').and.returnValue(of(new HttpResponse({ body: userExtraCollection })));
        const expectedCollection: IUserExtra[] = [userExtra, ...userExtraCollection];
        spyOn(userExtraService, 'addUserExtraToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ venditore });
        comp.ngOnInit();

        expect(userExtraService.query).toHaveBeenCalled();
        expect(userExtraService.addUserExtraToCollectionIfMissing).toHaveBeenCalledWith(userExtraCollection, userExtra);
        expect(comp.userExtrasCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const venditore: IVenditore = { id: 456 };
        const userExtra: IUserExtra = { id: 58652 };
        venditore.userExtra = userExtra;

        activatedRoute.data = of({ venditore });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(venditore));
        expect(comp.userExtrasCollection).toContain(userExtra);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const venditore = { id: 123 };
        spyOn(venditoreService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ venditore });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: venditore }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(venditoreService.update).toHaveBeenCalledWith(venditore);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const venditore = new Venditore();
        spyOn(venditoreService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ venditore });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: venditore }));
        saveSubject.complete();

        // THEN
        expect(venditoreService.create).toHaveBeenCalledWith(venditore);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const venditore = { id: 123 };
        spyOn(venditoreService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ venditore });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(venditoreService.update).toHaveBeenCalledWith(venditore);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackUserExtraById', () => {
        it('Should return tracked UserExtra primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackUserExtraById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
