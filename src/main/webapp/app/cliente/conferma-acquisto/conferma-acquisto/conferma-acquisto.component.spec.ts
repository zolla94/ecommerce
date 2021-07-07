import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfermaAcquistoComponent } from './conferma-acquisto.component';

describe('ConfermaAcquistoComponent', () => {
  let component: ConfermaAcquistoComponent;
  let fixture: ComponentFixture<ConfermaAcquistoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConfermaAcquistoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfermaAcquistoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
