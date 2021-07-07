jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ProdottoService } from '../service/prodotto.service';
import { IProdotto, Prodotto } from '../prodotto.model';
import { IVenditore } from 'app/entities/venditore/venditore.model';
import { VenditoreService } from 'app/entities/venditore/service/venditore.service';

import { ProdottoUpdateComponent } from './prodotto-update.component';

describe('Component Tests', () => {
  describe('Prodotto Management Update Component', () => {
    let comp: ProdottoUpdateComponent;
    let fixture: ComponentFixture<ProdottoUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let prodottoService: ProdottoService;
    let venditoreService: VenditoreService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ProdottoUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ProdottoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProdottoUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      prodottoService = TestBed.inject(ProdottoService);
      venditoreService = TestBed.inject(VenditoreService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Venditore query and add missing value', () => {
        const prodotto: IProdotto = { id: 456 };
        const venditore: IVenditore = { id: 60880 };
        prodotto.venditore = venditore;

        const venditoreCollection: IVenditore[] = [{ id: 12202 }];
        spyOn(venditoreService, 'query').and.returnValue(of(new HttpResponse({ body: venditoreCollection })));
        const additionalVenditores = [venditore];
        const expectedCollection: IVenditore[] = [...additionalVenditores, ...venditoreCollection];
        spyOn(venditoreService, 'addVenditoreToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ prodotto });
        comp.ngOnInit();

        expect(venditoreService.query).toHaveBeenCalled();
        expect(venditoreService.addVenditoreToCollectionIfMissing).toHaveBeenCalledWith(venditoreCollection, ...additionalVenditores);
        expect(comp.venditoresSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const prodotto: IProdotto = { id: 456 };
        const venditore: IVenditore = { id: 96242 };
        prodotto.venditore = venditore;

        activatedRoute.data = of({ prodotto });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(prodotto));
        expect(comp.venditoresSharedCollection).toContain(venditore);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const prodotto = { id: 123 };
        spyOn(prodottoService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ prodotto });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: prodotto }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(prodottoService.update).toHaveBeenCalledWith(prodotto);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const prodotto = new Prodotto();
        spyOn(prodottoService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ prodotto });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: prodotto }));
        saveSubject.complete();

        // THEN
        expect(prodottoService.create).toHaveBeenCalledWith(prodotto);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const prodotto = { id: 123 };
        spyOn(prodottoService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ prodotto });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(prodottoService.update).toHaveBeenCalledWith(prodotto);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackVenditoreById', () => {
        it('Should return tracked Venditore primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackVenditoreById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
