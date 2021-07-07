import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { ScontoComponent } from './list/sconto.component';
import { ScontoDetailComponent } from './detail/sconto-detail.component';
import { ScontoUpdateComponent } from './update/sconto-update.component';
import { ScontoDeleteDialogComponent } from './delete/sconto-delete-dialog.component';
import { ScontoRoutingModule } from './route/sconto-routing.module';

@NgModule({
  imports: [SharedModule, ScontoRoutingModule],
  declarations: [ScontoComponent, ScontoDetailComponent, ScontoUpdateComponent, ScontoDeleteDialogComponent],
  entryComponents: [ScontoDeleteDialogComponent],
})
export class ScontoModule {}
