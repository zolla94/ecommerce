import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ScontoDetailComponent } from './sconto-detail.component';

describe('Component Tests', () => {
  describe('Sconto Management Detail Component', () => {
    let comp: ScontoDetailComponent;
    let fixture: ComponentFixture<ScontoDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ScontoDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ sconto: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(ScontoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ScontoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load sconto on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sconto).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
