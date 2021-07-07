import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { VenditoreComponent } from './list/venditore.component';
import { VenditoreDetailComponent } from './detail/venditore-detail.component';
import { VenditoreUpdateComponent } from './update/venditore-update.component';
import { VenditoreDeleteDialogComponent } from './delete/venditore-delete-dialog.component';
import { VenditoreRoutingModule } from './route/venditore-routing.module';

@NgModule({
  imports: [SharedModule, VenditoreRoutingModule],
  declarations: [VenditoreComponent, VenditoreDetailComponent, VenditoreUpdateComponent, VenditoreDeleteDialogComponent],
  entryComponents: [VenditoreDeleteDialogComponent],
})
export class VenditoreModule {}
