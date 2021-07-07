import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IVenditore } from '../venditore.model';
import { VenditoreService } from '../service/venditore.service';
import { VenditoreDeleteDialogComponent } from '../delete/venditore-delete-dialog.component';

@Component({
  selector: 'jhi-venditore',
  templateUrl: './venditore.component.html',
})
export class VenditoreComponent implements OnInit {
  venditores?: IVenditore[];
  isLoading = false;

  constructor(protected venditoreService: VenditoreService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.venditoreService.query().subscribe(
      (res: HttpResponse<IVenditore[]>) => {
        this.isLoading = false;
        this.venditores = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IVenditore): number {
    return item.id!;
  }

  delete(venditore: IVenditore): void {
    const modalRef = this.modalService.open(VenditoreDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.venditore = venditore;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
