import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISconto, Sconto } from '../sconto.model';
import { ScontoService } from '../service/sconto.service';

@Injectable({ providedIn: 'root' })
export class ScontoRoutingResolveService implements Resolve<ISconto> {
  constructor(protected service: ScontoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISconto> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((sconto: HttpResponse<Sconto>) => {
          if (sconto.body) {
            return of(sconto.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Sconto());
  }
}
