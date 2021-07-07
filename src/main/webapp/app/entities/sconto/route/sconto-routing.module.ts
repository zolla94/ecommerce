import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ScontoComponent } from '../list/sconto.component';
import { ScontoDetailComponent } from '../detail/sconto-detail.component';
import { ScontoUpdateComponent } from '../update/sconto-update.component';
import { ScontoRoutingResolveService } from './sconto-routing-resolve.service';

const scontoRoute: Routes = [
  {
    path: '',
    component: ScontoComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ScontoDetailComponent,
    resolve: {
      sconto: ScontoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ScontoUpdateComponent,
    resolve: {
      sconto: ScontoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ScontoUpdateComponent,
    resolve: {
      sconto: ScontoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(scontoRoute)],
  exports: [RouterModule],
})
export class ScontoRoutingModule {}
