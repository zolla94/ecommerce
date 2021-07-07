import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProdotto, Prodotto } from '../prodotto.model';
import { ProdottoService } from '../service/prodotto.service';

@Injectable({ providedIn: 'root' })
export class ProdottoRoutingResolveService implements Resolve<IProdotto> {
  constructor(protected service: ProdottoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProdotto> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((prodotto: HttpResponse<Prodotto>) => {
          if (prodotto.body) {
            return of(prodotto.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Prodotto());
  }
}
