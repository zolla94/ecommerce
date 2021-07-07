import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IVenditore } from '../venditore.model';
import { VenditoreService } from '../service/venditore.service';

@Component({
  templateUrl: './venditore-delete-dialog.component.html',
})
export class VenditoreDeleteDialogComponent {
  venditore?: IVenditore;

  constructor(protected venditoreService: VenditoreService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.venditoreService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
