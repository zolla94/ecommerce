import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VenditoreDetailComponent } from './venditore-detail.component';

describe('Component Tests', () => {
  describe('Venditore Management Detail Component', () => {
    let comp: VenditoreDetailComponent;
    let fixture: ComponentFixture<VenditoreDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [VenditoreDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ venditore: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(VenditoreDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VenditoreDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load venditore on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.venditore).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
