import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { VenditoreComponent } from '../list/venditore.component';
import { VenditoreDetailComponent } from '../detail/venditore-detail.component';
import { VenditoreUpdateComponent } from '../update/venditore-update.component';
import { VenditoreRoutingResolveService } from './venditore-routing-resolve.service';

const venditoreRoute: Routes = [
  {
    path: '',
    component: VenditoreComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VenditoreDetailComponent,
    resolve: {
      venditore: VenditoreRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VenditoreUpdateComponent,
    resolve: {
      venditore: VenditoreRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VenditoreUpdateComponent,
    resolve: {
      venditore: VenditoreRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(venditoreRoute)],
  exports: [RouterModule],
})
export class VenditoreRoutingModule {}
