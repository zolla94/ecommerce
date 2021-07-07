import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISconto } from '../sconto.model';
import { ScontoService } from '../service/sconto.service';
import { ScontoDeleteDialogComponent } from '../delete/sconto-delete-dialog.component';

@Component({
  selector: 'jhi-sconto',
  templateUrl: './sconto.component.html',
})
export class ScontoComponent implements OnInit {
  scontos?: ISconto[];
  isLoading = false;

  constructor(protected scontoService: ScontoService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.scontoService.query().subscribe(
      (res: HttpResponse<ISconto[]>) => {
        this.isLoading = false;
        this.scontos = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ISconto): number {
    return item.id!;
  }

  delete(sconto: ISconto): void {
    const modalRef = this.modalService.open(ScontoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.sconto = sconto;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
