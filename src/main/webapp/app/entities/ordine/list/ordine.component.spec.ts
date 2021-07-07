import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { OrdineService } from '../service/ordine.service';

import { OrdineComponent } from './ordine.component';

describe('Component Tests', () => {
  describe('Ordine Management Component', () => {
    let comp: OrdineComponent;
    let fixture: ComponentFixture<OrdineComponent>;
    let service: OrdineService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [OrdineComponent],
      })
        .overrideTemplate(OrdineComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrdineComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(OrdineService);

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
      expect(comp.ordines?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
