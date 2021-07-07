import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOrdine } from '../ordine.model';
import { OrdineService } from '../service/ordine.service';
import { OrdineDeleteDialogComponent } from '../delete/ordine-delete-dialog.component';

@Component({
  selector: 'jhi-ordine',
  templateUrl: './ordine.component.html',
})
export class OrdineComponent implements OnInit {
  ordines?: IOrdine[];
  isLoading = false;

  constructor(protected ordineService: OrdineService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.ordineService.query().subscribe(
      (res: HttpResponse<IOrdine[]>) => {
        this.isLoading = false;
        this.ordines = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IOrdine): number {
    return item.id!;
  }

  delete(ordine: IOrdine): void {
    const modalRef = this.modalService.open(OrdineDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.ordine = ordine;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
