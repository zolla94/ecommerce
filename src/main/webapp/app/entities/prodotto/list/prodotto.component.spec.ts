import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { ProdottoService } from '../service/prodotto.service';

import { ProdottoComponent } from './prodotto.component';

describe('Component Tests', () => {
  describe('Prodotto Management Component', () => {
    let comp: ProdottoComponent;
    let fixture: ComponentFixture<ProdottoComponent>;
    let service: ProdottoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ProdottoComponent],
      })
        .overrideTemplate(ProdottoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProdottoComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(ProdottoService);

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
      expect(comp.prodottos?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
