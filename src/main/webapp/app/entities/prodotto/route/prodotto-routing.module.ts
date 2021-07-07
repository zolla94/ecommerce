import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProdottoComponent } from '../list/prodotto.component';
import { ProdottoDetailComponent } from '../detail/prodotto-detail.component';
import { ProdottoUpdateComponent } from '../update/prodotto-update.component';
import { ProdottoRoutingResolveService } from './prodotto-routing-resolve.service';
import { CarrelloComponent } from '../../../cliente/carrello/carrello.component';

const prodottoRoute: Routes = [
  {
    path: '',
    component: ProdottoComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProdottoDetailComponent,
    resolve: {
      prodotto: ProdottoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProdottoUpdateComponent,
    resolve: {
      prodotto: ProdottoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProdottoUpdateComponent,
    resolve: {
      prodotto: ProdottoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/cart/:quant/:tot',
    component: CarrelloComponent,
    resolve: {
      prodotto: ProdottoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(prodottoRoute)],
  exports: [RouterModule],
})
export class ProdottoRoutingModule {}
