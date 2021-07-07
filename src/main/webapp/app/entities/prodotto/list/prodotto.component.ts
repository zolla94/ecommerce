import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProdotto } from '../prodotto.model';
import { ProdottoService } from '../service/prodotto.service';
import { ProdottoDeleteDialogComponent } from '../delete/prodotto-delete-dialog.component';
import { AccountService } from '../../../core/auth/account.service';
import { Account } from 'app/core/auth/account.model';

@Component({
  selector: 'jhi-prodotto',
  templateUrl: './prodotto.component.html',
})
export class ProdottoComponent implements OnInit {
  account!: Account;
  prodottos?: IProdotto[];
  isLoading = false;

  constructor(protected prodottoService: ProdottoService, protected modalService: NgbModal, protected accountService: AccountService) {}

  loadAll(): void {
    this.isLoading = true;

    this.prodottoService.query().subscribe(
      (res: HttpResponse<IProdotto[]>) => {
        this.isLoading = false;
        this.prodottos = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );

    this.accountService.identity().subscribe(account => {
      if (account) {
        this.account = account;
      }
    });

  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IProdotto): number {
    return item.id!;
  }

  delete(prodotto: IProdotto): void {
    const modalRef = this.modalService.open(ProdottoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.prodotto = prodotto;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
