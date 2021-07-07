import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ScontiSettimanaliComponent } from './sconti-settimanali.component';

describe('ScontiSettimanaliComponent', () => {
  let component: ScontiSettimanaliComponent;
  let fixture: ComponentFixture<ScontiSettimanaliComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ScontiSettimanaliComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ScontiSettimanaliComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
