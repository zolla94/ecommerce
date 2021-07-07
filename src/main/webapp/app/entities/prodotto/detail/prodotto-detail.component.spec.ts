import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProdottoDetailComponent } from './prodotto-detail.component';

describe('Component Tests', () => {
  describe('Prodotto Management Detail Component', () => {
    let comp: ProdottoDetailComponent;
    let fixture: ComponentFixture<ProdottoDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ProdottoDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ prodotto: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(ProdottoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProdottoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load prodotto on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.prodotto).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
