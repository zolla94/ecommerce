import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';
import { ProdottoService } from '../../prodotto/service/prodotto.service';
import { Prodotto, IProdotto } from '../../prodotto/prodotto.model';

@Injectable({ providedIn: 'root' })
export class ProdottoRoutingResolveServiceAggiornamento implements Resolve<IProdotto> {

  constructor(protected service: ProdottoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProdotto> | Observable<never> {
    const id = route.params['idProd'];
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
