jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { OrdineService } from '../service/ordine.service';
import { IOrdine, Ordine } from '../ordine.model';
import { ICliente } from 'app/entities/cliente/cliente.model';
import { ClienteService } from 'app/entities/cliente/service/cliente.service';
import { IProdotto } from 'app/entities/prodotto/prodotto.model';
import { ProdottoService } from 'app/entities/prodotto/service/prodotto.service';
import { IVenditore } from 'app/entities/venditore/venditore.model';
import { VenditoreService } from 'app/entities/venditore/service/venditore.service';

import { OrdineUpdateComponent } from './ordine-update.component';

describe('Component Tests', () => {
  describe('Ordine Management Update Component', () => {
    let comp: OrdineUpdateComponent;
    let fixture: ComponentFixture<OrdineUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let ordineService: OrdineService;
    let clienteService: ClienteService;
    let prodottoService: ProdottoService;
    let venditoreService: VenditoreService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [OrdineUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(OrdineUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrdineUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      ordineService = TestBed.inject(OrdineService);
      clienteService = TestBed.inject(ClienteService);
      prodottoService = TestBed.inject(ProdottoService);
      venditoreService = TestBed.inject(VenditoreService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Cliente query and add missing value', () => {
        const ordine: IOrdine = { id: 456 };
        const cliente: ICliente = { id: 61076 };
        ordine.cliente = cliente;

        const clienteCollection: ICliente[] = [{ id: 69007 }];
        spyOn(clienteService, 'query').and.returnValue(of(new HttpResponse({ body: clienteCollection })));
        const additionalClientes = [cliente];
        const expectedCollection: ICliente[] = [...additionalClientes, ...clienteCollection];
        spyOn(clienteService, 'addClienteToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ ordine });
        comp.ngOnInit();

        expect(clienteService.query).toHaveBeenCalled();
        expect(clienteService.addClienteToCollectionIfMissing).toHaveBeenCalledWith(clienteCollection, ...additionalClientes);
        expect(comp.clientesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Prodotto query and add missing value', () => {
        const ordine: IOrdine = { id: 456 };
        const prodotto: IProdotto = { id: 17148 };
        ordine.prodotto = prodotto;

        const prodottoCollection: IProdotto[] = [{ id: 33515 }];
        spyOn(prodottoService, 'query').and.returnValue(of(new HttpResponse({ body: prodottoCollection })));
        const additionalProdottos = [prodotto];
        const expectedCollection: IProdotto[] = [...additionalProdottos, ...prodottoCollection];
        spyOn(prodottoService, 'addProdottoToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ ordine });
        comp.ngOnInit();

        expect(prodottoService.query).toHaveBeenCalled();
        expect(prodottoService.addProdottoToCollectionIfMissing).toHaveBeenCalledWith(prodottoCollection, ...additionalProdottos);
        expect(comp.prodottosSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Venditore query and add missing value', () => {
        const ordine: IOrdine = { id: 456 };
        const venditore: IVenditore = { id: 12213 };
        ordine.venditore = venditore;

        const venditoreCollection: IVenditore[] = [{ id: 50079 }];
        spyOn(venditoreService, 'query').and.returnValue(of(new HttpResponse({ body: venditoreCollection })));
        const additionalVenditores = [venditore];
        const expectedCollection: IVenditore[] = [...additionalVenditores, ...venditoreCollection];
        spyOn(venditoreService, 'addVenditoreToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ ordine });
        comp.ngOnInit();

        expect(venditoreService.query).toHaveBeenCalled();
        expect(venditoreService.addVenditoreToCollectionIfMissing).toHaveBeenCalledWith(venditoreCollection, ...additionalVenditores);
        expect(comp.venditoresSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const ordine: IOrdine = { id: 456 };
        const cliente: ICliente = { id: 41214 };
        ordine.cliente = cliente;
        const prodotto: IProdotto = { id: 78117 };
        ordine.prodotto = prodotto;
        const venditore: IVenditore = { id: 79462 };
        ordine.venditore = venditore;

        activatedRoute.data = of({ ordine });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(ordine));
        expect(comp.clientesSharedCollection).toContain(cliente);
        expect(comp.prodottosSharedCollection).toContain(prodotto);
        expect(comp.venditoresSharedCollection).toContain(venditore);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const ordine = { id: 123 };
        spyOn(ordineService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ ordine });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: ordine }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(ordineService.update).toHaveBeenCalledWith(ordine);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const ordine = new Ordine();
        spyOn(ordineService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ ordine });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: ordine }));
        saveSubject.complete();

        // THEN
        expect(ordineService.create).toHaveBeenCalledWith(ordine);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const ordine = { id: 123 };
        spyOn(ordineService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ ordine });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(ordineService.update).toHaveBeenCalledWith(ordine);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackClienteById', () => {
        it('Should return tracked Cliente primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackClienteById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackProdottoById', () => {
        it('Should return tracked Prodotto primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackProdottoById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

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
