jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ClienteService } from '../service/cliente.service';
import { ICliente, Cliente } from '../cliente.model';
import { IUserExtra } from 'app/entities/user-extra/user-extra.model';
import { UserExtraService } from 'app/entities/user-extra/service/user-extra.service';

import { ClienteUpdateComponent } from './cliente-update.component';

describe('Component Tests', () => {
  describe('Cliente Management Update Component', () => {
    let comp: ClienteUpdateComponent;
    let fixture: ComponentFixture<ClienteUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let clienteService: ClienteService;
    let userExtraService: UserExtraService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ClienteUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ClienteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ClienteUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      clienteService = TestBed.inject(ClienteService);
      userExtraService = TestBed.inject(UserExtraService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call userExtra query and add missing value', () => {
        const cliente: ICliente = { id: 456 };
        const userExtra: IUserExtra = { id: 13048 };
        cliente.userExtra = userExtra;

        const userExtraCollection: IUserExtra[] = [{ id: 96102 }];
        spyOn(userExtraService, 'query').and.returnValue(of(new HttpResponse({ body: userExtraCollection })));
        const expectedCollection: IUserExtra[] = [userExtra, ...userExtraCollection];
        spyOn(userExtraService, 'addUserExtraToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ cliente });
        comp.ngOnInit();

        expect(userExtraService.query).toHaveBeenCalled();
        expect(userExtraService.addUserExtraToCollectionIfMissing).toHaveBeenCalledWith(userExtraCollection, userExtra);
        expect(comp.userExtrasCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const cliente: ICliente = { id: 456 };
        const userExtra: IUserExtra = { id: 73733 };
        cliente.userExtra = userExtra;

        activatedRoute.data = of({ cliente });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(cliente));
        expect(comp.userExtrasCollection).toContain(userExtra);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const cliente = { id: 123 };
        spyOn(clienteService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ cliente });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: cliente }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(clienteService.update).toHaveBeenCalledWith(cliente);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const cliente = new Cliente();
        spyOn(clienteService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ cliente });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: cliente }));
        saveSubject.complete();

        // THEN
        expect(clienteService.create).toHaveBeenCalledWith(cliente);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const cliente = { id: 123 };
        spyOn(clienteService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ cliente });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(clienteService.update).toHaveBeenCalledWith(cliente);
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
