jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IProdotto, Prodotto } from '../prodotto.model';
import { ProdottoService } from '../service/prodotto.service';

import { ProdottoRoutingResolveService } from './prodotto-routing-resolve.service';

describe('Service Tests', () => {
  describe('Prodotto routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: ProdottoRoutingResolveService;
    let service: ProdottoService;
    let resultProdotto: IProdotto | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(ProdottoRoutingResolveService);
      service = TestBed.inject(ProdottoService);
      resultProdotto = undefined;
    });

    describe('resolve', () => {
      it('should return IProdotto returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultProdotto = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultProdotto).toEqual({ id: 123 });
      });

      it('should return new IProdotto if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultProdotto = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultProdotto).toEqual(new Prodotto());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultProdotto = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultProdotto).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
