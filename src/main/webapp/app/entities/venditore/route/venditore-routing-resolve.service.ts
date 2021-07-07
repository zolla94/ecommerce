import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IVenditore, Venditore } from '../venditore.model';
import { VenditoreService } from '../service/venditore.service';

@Injectable({ providedIn: 'root' })
export class VenditoreRoutingResolveService implements Resolve<IVenditore> {
  constructor(protected service: VenditoreService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVenditore> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((venditore: HttpResponse<Venditore>) => {
          if (venditore.body) {
            return of(venditore.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Venditore());
  }
}
