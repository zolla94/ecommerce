import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IProdotto, Prodotto } from '../prodotto.model';
import { ProdottoService } from '../service/prodotto.service';
import { IVenditore } from 'app/entities/venditore/venditore.model';
import { VenditoreService } from 'app/entities/venditore/service/venditore.service';
import { AccountService } from '../../../core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { AccountVenditore } from '../../../core/auth/account.model';

@Component({
  selector: 'jhi-prodotto-update',
  templateUrl: './prodotto-update.component.html',
})
export class ProdottoUpdateComponent implements OnInit {
  isSaving = false;

  venditoresSharedCollection: IVenditore[] = [];
  prodotto?: IProdotto[];
  p?: IProdotto;
  account!: Account;
  venditore!: AccountVenditore;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    descrizione: [null, [Validators.required]],
    prezzo: [null, [Validators.required]],
    disponibilita: [],
    categoria: [null, [Validators.required]],
    imageUrl: [],
    venditore: [],
  });

  constructor(
    protected prodottoService: ProdottoService,
    protected venditoreService: VenditoreService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder,
    protected accountService: AccountService
  ) {}

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => {
      if (account) {
        this.account = account;
      }
    });

    this.activatedRoute.data.subscribe(({ prodotto }) => {
      this.accountService.fetchVenditore().subscribe(venditore => {
        this.venditore = venditore;
      });
      this.updateForm(prodotto);
      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    this.accountService.fetchVenditore().subscribe(venditore => {
      this.venditore = venditore;
    })
    const prodotto = this.createFromForm();
    if (prodotto.id !== undefined) {
      this.subscribeToSaveResponse(this.prodottoService.update(prodotto));
    } else {
      this.subscribeToSaveResponse(this.prodottoService.create(prodotto));
    }
  }

  trackVenditoreById(index: number, item: IVenditore): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProdotto>>): void {
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

  protected updateForm(prodotto: IProdotto): void {
    this.editForm.patchValue({
      id: prodotto.id,
      nome: prodotto.nome,
      descrizione: prodotto.descrizione,
      prezzo: prodotto.prezzo,
      disponibilita: prodotto.disponibilita,
      categoria: prodotto.categoria,
      imageUrl: prodotto.imageUrl,
      venditore: prodotto.venditore
    });

    this.venditoresSharedCollection = this.venditoreService.addVenditoreToCollectionIfMissing(
      this.venditoresSharedCollection,
      prodotto.venditore
    );
  }

  protected loadRelationshipsOptions(): void {
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

  protected createFromForm(): IProdotto {
    return {
      ...new Prodotto(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      descrizione: this.editForm.get(['descrizione'])!.value,
      prezzo: this.editForm.get(['prezzo'])!.value,
      disponibilita: this.editForm.get(['disponibilita'])!.value,
      categoria: this.editForm.get(['categoria'])!.value,
      imageUrl: this.editForm.get(['imageUrl'])!.value,
      venditore: this.venditore,
    };
  }
}
