import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { Cat } from 'app/entities/enumerations/cat.model';
import { IProdotto, Prodotto } from '../prodotto.model';

import { ProdottoService } from './prodotto.service';

describe('Service Tests', () => {
  describe('Prodotto Service', () => {
    let service: ProdottoService;
    let httpMock: HttpTestingController;
    let elemDefault: IProdotto;
    let expectedResult: IProdotto | IProdotto[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ProdottoService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        nome: 'AAAAAAA',
        descrizione: 'AAAAAAA',
        prezzo: 0,
        disponibilita: 0,
        categoria: Cat.ACTIONFIGURE,
        imageUrl: 'AAAAAAA',
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

      it('should create a Prodotto', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Prodotto()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Prodotto', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            nome: 'BBBBBB',
            descrizione: 'BBBBBB',
            prezzo: 1,
            disponibilita: 1,
            categoria: 'BBBBBB',
            imageUrl: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Prodotto', () => {
        const patchObject = Object.assign(
          {
            nome: 'BBBBBB',
            descrizione: 'BBBBBB',
            prezzo: 1,
            disponibilita: 1,
          },
          new Prodotto()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Prodotto', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            nome: 'BBBBBB',
            descrizione: 'BBBBBB',
            prezzo: 1,
            disponibilita: 1,
            categoria: 'BBBBBB',
            imageUrl: 'BBBBBB',
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

      it('should delete a Prodotto', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addProdottoToCollectionIfMissing', () => {
        it('should add a Prodotto to an empty array', () => {
          const prodotto: IProdotto = { id: 123 };
          expectedResult = service.addProdottoToCollectionIfMissing([], prodotto);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(prodotto);
        });

        it('should not add a Prodotto to an array that contains it', () => {
          const prodotto: IProdotto = { id: 123 };
          const prodottoCollection: IProdotto[] = [
            {
              ...prodotto,
            },
            { id: 456 },
          ];
          expectedResult = service.addProdottoToCollectionIfMissing(prodottoCollection, prodotto);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Prodotto to an array that doesn't contain it", () => {
          const prodotto: IProdotto = { id: 123 };
          const prodottoCollection: IProdotto[] = [{ id: 456 }];
          expectedResult = service.addProdottoToCollectionIfMissing(prodottoCollection, prodotto);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(prodotto);
        });

        it('should add only unique Prodotto to an array', () => {
          const prodottoArray: IProdotto[] = [{ id: 123 }, { id: 456 }, { id: 49076 }];
          const prodottoCollection: IProdotto[] = [{ id: 123 }];
          expectedResult = service.addProdottoToCollectionIfMissing(prodottoCollection, ...prodottoArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const prodotto: IProdotto = { id: 123 };
          const prodotto2: IProdotto = { id: 456 };
          expectedResult = service.addProdottoToCollectionIfMissing([], prodotto, prodotto2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(prodotto);
          expect(expectedResult).toContain(prodotto2);
        });

        it('should accept null and undefined values', () => {
          const prodotto: IProdotto = { id: 123 };
          expectedResult = service.addProdottoToCollectionIfMissing([], null, prodotto, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(prodotto);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
