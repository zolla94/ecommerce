import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AccountVenditore } from '../../core/auth/account.model';
import { ProdottoDeleteDialogComponent } from '../../entities/prodotto/delete/prodotto-delete-dialog.component';
import { IProdotto } from '../../entities/prodotto/prodotto.model';
import { VenditoreService } from '../venditore.service';

@Component({
  selector: 'jhi-venditore',
  templateUrl: './gestione.component.html',
  styleUrls: ['./gestione.component.scss']
})
export class GestioneComponent implements OnInit {
  prodotto?: IProdotto[];
  isLoading = false;
  venditore!: AccountVenditore;
  
  constructor(private venditoreService: VenditoreService, protected modalService: NgbModal) {}

  ngOnInit(): void {
    this.viewProdotti();
  }

  trackId(index: number, item: IProdotto): number {
    return item.id!;
  }

  viewProdotti(): void {
    this.venditoreService.tuttiProdotti().subscribe(
      res => {
        this.prodotto = res.body ?? [];
      },
    );
  }

  delete(prodotto: IProdotto): void {
    const modalRef = this.modalService.open(ProdottoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.prodotto = prodotto;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.viewProdotti();
      }
    });
  }
}
