import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISconto, Sconto } from '../sconto.model';

import { ScontoService } from './sconto.service';

describe('Service Tests', () => {
  describe('Sconto Service', () => {
    let service: ScontoService;
    let httpMock: HttpTestingController;
    let elemDefault: ISconto;
    let expectedResult: ISconto | ISconto[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ScontoService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        nome: 'AAAAAAA',
        giorni: 'AAAAAAA',
        valore: 0,
        cat: 'AAAAAAA',
        attivo: false,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Sconto', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Sconto()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Sconto', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            nome: 'BBBBBB',
            giorni: 'BBBBBB',
            valore: 1,
            cat: 'BBBBBB',
            attivo: true,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Sconto', () => {
        const patchObject = Object.assign(
          {
            nome: 'BBBBBB',
            giorni: 'BBBBBB',
            valore: 1,
          },
          new Sconto()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Sconto', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            nome: 'BBBBBB',
            giorni: 'BBBBBB',
            valore: 1,
            cat: 'BBBBBB',
            attivo: true,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Sconto', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addScontoToCollectionIfMissing', () => {
        it('should add a Sconto to an empty array', () => {
          const sconto: ISconto = { id: 123 };
          expectedResult = service.addScontoToCollectionIfMissing([], sconto);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(sconto);
        });

        it('should not add a Sconto to an array that contains it', () => {
          const sconto: ISconto = { id: 123 };
          const scontoCollection: ISconto[] = [
            {
              ...sconto,
            },
            { id: 456 },
          ];
          expectedResult = service.addScontoToCollectionIfMissing(scontoCollection, sconto);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Sconto to an array that doesn't contain it", () => {
          const sconto: ISconto = { id: 123 };
          const scontoCollection: ISconto[] = [{ id: 456 }];
          expectedResult = service.addScontoToCollectionIfMissing(scontoCollection, sconto);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(sconto);
        });

        it('should add only unique Sconto to an array', () => {
          const scontoArray: ISconto[] = [{ id: 123 }, { id: 456 }, { id: 40976 }];
          const scontoCollection: ISconto[] = [{ id: 123 }];
          expectedResult = service.addScontoToCollectionIfMissing(scontoCollection, ...scontoArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const sconto: ISconto = { id: 123 };
          const sconto2: ISconto = { id: 456 };
          expectedResult = service.addScontoToCollectionIfMissing([], sconto, sconto2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(sconto);
          expect(expectedResult).toContain(sconto2);
        });

        it('should accept null and undefined values', () => {
          const sconto: ISconto = { id: 123 };
          expectedResult = service.addScontoToCollectionIfMissing([], null, sconto, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(sconto);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
