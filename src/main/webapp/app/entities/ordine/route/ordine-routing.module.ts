import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { OrdineComponent } from '../list/ordine.component';
import { OrdineDetailComponent } from '../detail/ordine-detail.component';
import { OrdineUpdateComponent } from '../update/ordine-update.component';
import { OrdineRoutingResolveService } from './ordine-routing-resolve.service';
import { OrdiniComponent } from '../../../venditore/ordini/ordini.component';
import { RiepilogoComponent } from '../../../cliente/riepilogo/riepilogo.component';
import { ProdottoRoutingResolveServiceAggiornamento } from './prodotto-routing-resolve-aggiornamento.service';

const ordineRoute: Routes = [
  {
    path: '',
    component: OrdineComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OrdineDetailComponent,
    resolve: {
      ordine: OrdineRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OrdineUpdateComponent,
    resolve: {
      ordine: OrdineRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OrdineUpdateComponent,
    resolve: {
      ordine: OrdineRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/spedito',
    component: OrdiniComponent, // ci posso mettere una modale
    resolve: {
      ordine: OrdineRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/acquista/:disp/:idProd/:tot',
    component: RiepilogoComponent, // ci posso mettere una modale
    resolve: {
      ordine: OrdineRoutingResolveService,
      prodotto: ProdottoRoutingResolveServiceAggiornamento,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(ordineRoute)],
  exports: [RouterModule],
})
export class OrdineRoutingModule {}
