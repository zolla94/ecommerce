import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IOrdine, Ordine } from '../ordine.model';
import { OrdineService } from '../service/ordine.service';
import { ICliente } from 'app/entities/cliente/cliente.model';
import { ClienteService } from 'app/entities/cliente/service/cliente.service';
import { IProdotto } from 'app/entities/prodotto/prodotto.model';
import { ProdottoService } from 'app/entities/prodotto/service/prodotto.service';
import { IVenditore } from 'app/entities/venditore/venditore.model';
import { VenditoreService } from 'app/entities/venditore/service/venditore.service';

@Component({
  selector: 'jhi-ordine-update',
  templateUrl: './ordine-update.component.html',
})
export class OrdineUpdateComponent implements OnInit {
  isSaving = false;

  clientesSharedCollection: ICliente[] = [];
  prodottosSharedCollection: IProdotto[] = [];
  venditoresSharedCollection: IVenditore[] = [];

  editForm = this.fb.group({
    id: [],
    acquistato: [null, [Validators.required]],
    spedito: [],
    quantita: [null, [Validators.required]],
    totale: [null, [Validators.required]],
    cliente: [],
    prodotto: [],
    venditore: [],
  });

  constructor(
    protected ordineService: OrdineService,
    protected clienteService: ClienteService,
    protected prodottoService: ProdottoService,
    protected venditoreService: VenditoreService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ordine }) => {
      this.updateForm(ordine);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ordine = this.createFromForm();
    if (ordine.id !== undefined) {
      this.subscribeToSaveResponse(this.ordineService.update(ordine));
    } else {
      this.subscribeToSaveResponse(this.ordineService.create(ordine));
    }
  }

  trackClienteById(index: number, item: ICliente): number {
    return item.id!;
  }

  trackProdottoById(index: number, item: IProdotto): number {
    return item.id!;
  }

  trackVenditoreById(index: number, item: IVenditore): number {
    return item.id!;
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

  protected updateForm(ordine: IOrdine): void {
    this.editForm.patchValue({
      id: ordine.id,
      acquistato: ordine.acquistato,
      spedito: ordine.spedito,
      quantita: ordine.quantita,
      totale: ordine.totale,
      cliente: ordine.cliente,
      prodotto: ordine.prodotto,
      venditore: ordine.venditore,
    });

    this.clientesSharedCollection = this.clienteService.addClienteToCollectionIfMissing(this.clientesSharedCollection, ordine.cliente);
    this.prodottosSharedCollection = this.prodottoService.addProdottoToCollectionIfMissing(this.prodottosSharedCollection, ordine.prodotto);
    this.venditoresSharedCollection = this.venditoreService.addVenditoreToCollectionIfMissing(
      this.venditoresSharedCollection,
      ordine.venditore
    );
  }

  protected loadRelationshipsOptions(): void {
    this.clienteService
      .query()
      .pipe(map((res: HttpResponse<ICliente[]>) => res.body ?? []))
      .pipe(
        map((clientes: ICliente[]) => this.clienteService.addClienteToCollectionIfMissing(clientes, this.editForm.get('cliente')!.value))
      )
      .subscribe((clientes: ICliente[]) => (this.clientesSharedCollection = clientes));

    this.prodottoService
      .query()
      .pipe(map((res: HttpResponse<IProdotto[]>) => res.body ?? []))
      .pipe(
        map((prodottos: IProdotto[]) =>
          this.prodottoService.addProdottoToCollectionIfMissing(prodottos, this.editForm.get('prodotto')!.value)
        )
      )
      .subscribe((prodottos: IProdotto[]) => (this.prodottosSharedCollection = prodottos));

    this.venditoreService
      .query()
      .pipe(map((res: HttpResponse<IVenditore[]>) => res.body ?? []))
      .pipe(
        map((venditores: IVenditore[]) =>
          this.venditoreService.addVenditoreToCollectionIfMissing(venditores, this.editForm.get('venditore')!.value)
        )
      )
      .subscribe((venditores: IVenditore[]) => (this.venditoresSharedCollection = venditores));
  }

  protected createFromForm(): IOrdine {
    return {
      ...new Ordine(),
      id: this.editForm.get(['id'])!.value,
      acquistato: this.editForm.get(['acquistato'])!.value,
      spedito: this.editForm.get(['spedito'])!.value,
      quantita: this.editForm.get(['quantita'])!.value,
      totale: this.editForm.get(['totale'])!.value,
      cliente: this.editForm.get(['cliente'])!.value,
      prodotto: this.editForm.get(['prodotto'])!.value,
      venditore: this.editForm.get(['venditore'])!.value,
    };
  }
}
