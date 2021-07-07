jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IOrdine, Ordine } from '../ordine.model';
import { OrdineService } from '../service/ordine.service';

import { OrdineRoutingResolveService } from './ordine-routing-resolve.service';

describe('Service Tests', () => {
  describe('Ordine routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: OrdineRoutingResolveService;
    let service: OrdineService;
    let resultOrdine: IOrdine | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(OrdineRoutingResolveService);
      service = TestBed.inject(OrdineService);
      resultOrdine = undefined;
    });

    describe('resolve', () => {
      it('should return IOrdine returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultOrdine = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultOrdine).toEqual({ id: 123 });
      });

      it('should return new IOrdine if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultOrdine = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultOrdine).toEqual(new Ordine());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultOrdine = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultOrdine).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
