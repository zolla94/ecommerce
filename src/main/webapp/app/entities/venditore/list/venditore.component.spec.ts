import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { VenditoreService } from '../service/venditore.service';

import { VenditoreComponent } from './venditore.component';

describe('Component Tests', () => {
  describe('Venditore Management Component', () => {
    let comp: VenditoreComponent;
    let fixture: ComponentFixture<VenditoreComponent>;
    let service: VenditoreService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [VenditoreComponent],
      })
        .overrideTemplate(VenditoreComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VenditoreComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(VenditoreService);

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
      expect(comp.venditores?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
