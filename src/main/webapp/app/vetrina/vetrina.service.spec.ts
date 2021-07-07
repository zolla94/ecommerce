import { TestBed } from '@angular/core/testing';

import { VetrinaService } from './vetrina.service';

describe('VetrinaService', () => {
  let service: VetrinaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VetrinaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
