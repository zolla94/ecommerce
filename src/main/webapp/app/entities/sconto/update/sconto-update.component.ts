import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ISconto, Sconto } from '../sconto.model';
import { ScontoService } from '../service/sconto.service';

@Component({
  selector: 'jhi-sconto-update',
  templateUrl: './sconto-update.component.html',
})
export class ScontoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [],
    giorni: [],
    valore: [],
    cat: [],
    attivo: [],
  });

  constructor(protected scontoService: ScontoService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sconto }) => {
      this.updateForm(sconto);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sconto = this.createFromForm();
    if (sconto.id !== undefined) {
      this.subscribeToSaveResponse(this.scontoService.update(sconto));
    } else {
      this.subscribeToSaveResponse(this.scontoService.create(sconto));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISconto>>): void {
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

  protected updateForm(sconto: ISconto): void {
    this.editForm.patchValue({
      id: sconto.id,
      nome: sconto.nome,
      giorni: sconto.giorni,
      valore: sconto.valore,
      cat: sconto.cat,
      attivo: sconto.attivo,
    });
  }

  protected createFromForm(): ISconto {
    return {
      ...new Sconto(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      giorni: this.editForm.get(['giorni'])!.value,
      valore: this.editForm.get(['valore'])!.value,
      cat: this.editForm.get(['cat'])!.value,
      attivo: this.editForm.get(['attivo'])!.value,
    };
  }
}
