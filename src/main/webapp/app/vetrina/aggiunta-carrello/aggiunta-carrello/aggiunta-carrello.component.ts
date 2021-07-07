import { HttpResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';
import { AccountCliente } from '../../../core/auth/account.model';
import { AccountService } from '../../../core/auth/account.service';
import { IOrdine, Ordine } from '../../../entities/ordine/ordine.model';
import { OrdineService } from '../../../entities/ordine/service/ordine.service';
import { IProdotto } from '../../../entities/prodotto/prodotto.model';

@Component({
  selector: 'jhi-aggiunta-carrello',
  templateUrl: './aggiunta-carrello.component.html',
  styleUrls: ['./aggiunta-carrello.component.scss']
})
export class AggiuntaCarrelloComponent {

  q!: number;
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    acquistato: [null, [Validators.required]],
    spedito: [],
    quantita: [null, [Validators.required]],
    cliente: [],
    prodotto: [],
    venditore: [],
  });

  constructor(
    public activeModal: NgbActiveModal,
    private activatedRoute: ActivatedRoute,
    private accountService: AccountService,
    private ordineService: OrdineService,
    private fb: FormBuilder,
    private route: Router
  ) { }

  trackId(index: number, item: IOrdine): number {
    return item.id!;
  }

  save(prodotto: IProdotto, q: number | undefined): void {
    this.route.navigate(['vetrina'])
    this.isSaving = true;
    this.accountService.fetchCliente().subscribe(c => {
      const ordine = this.createFromForm(prodotto, c, q);
      if (ordine.id !== undefined) {
        this.subscribeToSaveResponse(this.ordineService.update(ordine));
      } else {
        this.subscribeToSaveResponse(this.ordineService.create(ordine));
      }
    })
  }

  ok(): void {
    this.activatedRoute.data.subscribe(({ prodotto }) => {
      this.q = +this.activatedRoute.snapshot.paramMap.get('quant')!;
      this.save(prodotto, this.q);
    });
    this.activeModal.dismiss();
    this.route.navigate(['vetrina'])
  }


  previousState(): void {
    window.history.back();
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrdine>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected createFromForm(p: IProdotto, c: AccountCliente, q: number | undefined): IOrdine {
    return {
      ...new Ordine(),
      id: undefined,
      acquistato: false,
      spedito: false,
      quantita: q,
      cliente: c,
      prodotto: p,
      venditore: p.venditore,
    };
  }

}
