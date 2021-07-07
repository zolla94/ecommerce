import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISconto } from '../sconto.model';
import { ScontoService } from '../service/sconto.service';

@Component({
  templateUrl: './sconto-delete-dialog.component.html',
})
export class ScontoDeleteDialogComponent {
  sconto?: ISconto;

  constructor(protected scontoService: ScontoService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.scontoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
