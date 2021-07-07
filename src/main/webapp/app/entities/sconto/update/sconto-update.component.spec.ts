jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ScontoService } from '../service/sconto.service';
import { ISconto, Sconto } from '../sconto.model';

import { ScontoUpdateComponent } from './sconto-update.component';

describe('Component Tests', () => {
  describe('Sconto Management Update Component', () => {
    let comp: ScontoUpdateComponent;
    let fixture: ComponentFixture<ScontoUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let scontoService: ScontoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ScontoUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ScontoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ScontoUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      scontoService = TestBed.inject(ScontoService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const sconto: ISconto = { id: 456 };

        activatedRoute.data = of({ sconto });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(sconto));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const sconto = { id: 123 };
        spyOn(scontoService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ sconto });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: sconto }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(scontoService.update).toHaveBeenCalledWith(sconto);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const sconto = new Sconto();
        spyOn(scontoService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ sconto });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: sconto }));
        saveSubject.complete();

        // THEN
        expect(scontoService.create).toHaveBeenCalledWith(sconto);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const sconto = { id: 123 };
        spyOn(scontoService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ sconto });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(scontoService.update).toHaveBeenCalledWith(sconto);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
