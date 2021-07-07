import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { UserExtraComponent } from '../list/user-extra.component';
import { UserExtraDetailComponent } from '../detail/user-extra-detail.component';
import { UserExtraUpdateComponent } from '../update/user-extra-update.component';
import { UserExtraRoutingResolveService } from './user-extra-routing-resolve.service';

const userExtraRoute: Routes = [
  {
    path: '',
    component: UserExtraComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserExtraDetailComponent,
    resolve: {
      userExtra: UserExtraRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserExtraUpdateComponent,
    resolve: {
      userExtra: UserExtraRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserExtraUpdateComponent,
    resolve: {
      userExtra: UserExtraRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(userExtraRoute)],
  exports: [RouterModule],
})
export class UserExtraRoutingModule {}
