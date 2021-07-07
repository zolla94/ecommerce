import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { errorRoute } from './layouts/error/error.route';
import { navbarRoute } from './layouts/navbar/navbar.route';
import { DEBUG_INFO_ENABLED } from 'app/app.constants';
import { Authority } from 'app/config/authority.constants';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { GestioneComponent } from './venditore/gestione/gestione.component';
import { OrdiniComponent } from './venditore/ordini/ordini.component';
import { CarrelloComponent } from './cliente/carrello/carrello.component';
import { RiepilogoComponent } from './cliente/riepilogo/riepilogo.component';
import { VetrinaComponent } from './vetrina/vetrina.component';
import { ScontiSettimanaliComponent } from './sconti-settimanali/sconti-settimanali.component';

const LAYOUT_ROUTES = [navbarRoute, ...errorRoute];

@NgModule({
  imports: [
    RouterModule.forRoot(
      [
        {
          path: 'admin',
          data: {
            authorities: [Authority.ADMIN],
          },
          canActivate: [UserRouteAccessService],
          loadChildren: () => import('./admin/admin-routing.module').then(m => m.AdminRoutingModule),
        },
        {
          path: 'account',
          loadChildren: () => import('./account/account.module').then(m => m.AccountModule),
        },
        {
          path: 'login',
          loadChildren: () => import('./login/login.module').then(m => m.LoginModule),
        },
        {
          data: {
            authorities: [Authority.SELLER],
          },
          path: 'gestione',
          component: GestioneComponent,
        },
        {
          data: {
            authorities: [Authority.SELLER],
          },
          path: 'ordini',
          component: OrdiniComponent,
        },
        {
          data: {
            authorities: [Authority.CUSTOMER],
          },
          path: 'carrello',
          component: CarrelloComponent,
        },
        {
          data: {
            authorities: [Authority.CUSTOMER],
          },
          path: 'riepilogo',
          component: RiepilogoComponent,
        },
        {
          data: {
            authorities: [Authority.ADMIN],
          },
          path: 'scontiSettimanali',
          component: ScontiSettimanaliComponent,
        },
        {
          data: {
          },
          path: 'vetrina',
          component: VetrinaComponent,
        },
        ...LAYOUT_ROUTES,
      ],
      { enableTracing: DEBUG_INFO_ENABLED }
    ),
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}
