import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { ScontoService } from '../service/sconto.service';

import { ScontoComponent } from './sconto.component';

describe('Component Tests', () => {
  describe('Sconto Management Component', () => {
    let comp: ScontoComponent;
    let fixture: ComponentFixture<ScontoComponent>;
    let service: ScontoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ScontoComponent],
      })
        .overrideTemplate(ScontoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ScontoComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(ScontoService);

      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [{ id: 123 }],
            headers,
          })
        )
      );
    });

    it('Should call load all on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.scontos?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
