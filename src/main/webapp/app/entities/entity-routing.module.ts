import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { Authority } from 'app/config/authority.constants';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'user-extra',
        data: {
          authorities: [Authority.ADMIN, Authority.SELLER], 
          pageTitle: 'UserExtras'
        },
        loadChildren: () => import('./user-extra/user-extra.module').then(m => m.UserExtraModule),
      },
      {
        path: 'cliente',
        data: {authorities: [Authority.ADMIN, Authority.SELLER, Authority.CUSTOMER], pageTitle: 'Clientes' },
        loadChildren: () => import('./cliente/cliente.module').then(m => m.ClienteModule),
      },
      {
        path: 'venditore',
        data: {authorities: [Authority.ADMIN], pageTitle: 'Venditores' },
        loadChildren: () => import('./venditore/venditore.module').then(m => m.VenditoreModule),
      },
      {
        path: 'sconto',
        data: {authorities: [Authority.ADMIN], pageTitle: 'Scontos' },
        loadChildren: () => import('./sconto/sconto.module').then(m => m.ScontoModule),
      },
      {
        path: 'prodotto',
        data: { pageTitle: 'Prodottos' },
        loadChildren: () => import('./prodotto/prodotto.module').then(m => m.ProdottoModule),
      },
      {
        path: 'ordine',
        data: {authorities: [Authority.ADMIN, Authority.SELLER, Authority.CUSTOMER], pageTitle: 'Ordines' },
        loadChildren: () => import('./ordine/ordine.module').then(m => m.OrdineModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
