import { TestBed } from '@angular/core/testing';

import { ScontiSettimanaliService } from './sconti-settimanali.service';

describe('ScontiSettimanaliService', () => {
  let service: ScontiSettimanaliService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ScontiSettimanaliService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
