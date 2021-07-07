import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { ProdottoComponent } from './list/prodotto.component';
import { ProdottoDetailComponent } from './detail/prodotto-detail.component';
import { ProdottoUpdateComponent } from './update/prodotto-update.component';
import { ProdottoDeleteDialogComponent } from './delete/prodotto-delete-dialog.component';
import { ProdottoRoutingModule } from './route/prodotto-routing.module';

@NgModule({
  imports: [SharedModule, ProdottoRoutingModule],
  declarations: [ProdottoComponent, ProdottoDetailComponent, ProdottoUpdateComponent, ProdottoDeleteDialogComponent],
  entryComponents: [ProdottoDeleteDialogComponent],
})
export class ProdottoModule {}
