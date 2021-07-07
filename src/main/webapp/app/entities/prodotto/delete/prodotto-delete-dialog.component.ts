import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IProdotto } from '../prodotto.model';
import { ProdottoService } from '../service/prodotto.service';

@Component({
  templateUrl: './prodotto-delete-dialog.component.html',
})
export class ProdottoDeleteDialogComponent {
  prodotto?: IProdotto;

  constructor(protected prodottoService: ProdottoService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.prodottoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
