import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AggiuntaCarrelloComponent } from './aggiunta-carrello.component';

describe('AggiuntaCarrelloComponent', () => {
  let component: AggiuntaCarrelloComponent;
  let fixture: ComponentFixture<AggiuntaCarrelloComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AggiuntaCarrelloComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AggiuntaCarrelloComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
