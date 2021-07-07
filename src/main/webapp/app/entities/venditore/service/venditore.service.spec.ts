import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IVenditore, Venditore } from '../venditore.model';

import { VenditoreService } from './venditore.service';

describe('Service Tests', () => {
  describe('Venditore Service', () => {
    let service: VenditoreService;
    let httpMock: HttpTestingController;
    let elemDefault: IVenditore;
    let expectedResult: IVenditore | IVenditore[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(VenditoreService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
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

      it('should create a Venditore', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Venditore()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Venditore', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Venditore', () => {
        const patchObject = Object.assign({}, new Venditore());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Venditore', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
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

      it('should delete a Venditore', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addVenditoreToCollectionIfMissing', () => {
        it('should add a Venditore to an empty array', () => {
          const venditore: IVenditore = { id: 123 };
          expectedResult = service.addVenditoreToCollectionIfMissing([], venditore);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(venditore);
        });

        it('should not add a Venditore to an array that contains it', () => {
          const venditore: IVenditore = { id: 123 };
          const venditoreCollection: IVenditore[] = [
            {
              ...venditore,
            },
            { id: 456 },
          ];
          expectedResult = service.addVenditoreToCollectionIfMissing(venditoreCollection, venditore);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Venditore to an array that doesn't contain it", () => {
          const venditore: IVenditore = { id: 123 };
          const venditoreCollection: IVenditore[] = [{ id: 456 }];
          expectedResult = service.addVenditoreToCollectionIfMissing(venditoreCollection, venditore);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(venditore);
        });

        it('should add only unique Venditore to an array', () => {
          const venditoreArray: IVenditore[] = [{ id: 123 }, { id: 456 }, { id: 33404 }];
          const venditoreCollection: IVenditore[] = [{ id: 123 }];
          expectedResult = service.addVenditoreToCollectionIfMissing(venditoreCollection, ...venditoreArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const venditore: IVenditore = { id: 123 };
          const venditore2: IVenditore = { id: 456 };
          expectedResult = service.addVenditoreToCollectionIfMissing([], venditore, venditore2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(venditore);
          expect(expectedResult).toContain(venditore2);
        });

        it('should accept null and undefined values', () => {
          const venditore: IVenditore = { id: 123 };
          expectedResult = service.addVenditoreToCollectionIfMissing([], null, venditore, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(venditore);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
