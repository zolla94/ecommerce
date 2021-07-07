import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ICliente, Cliente } from '../cliente.model';
import { ClienteService } from '../service/cliente.service';
import { IUserExtra } from 'app/entities/user-extra/user-extra.model';
import { UserExtraService } from 'app/entities/user-extra/service/user-extra.service';

@Component({
  selector: 'jhi-cliente-update',
  templateUrl: './cliente-update.component.html',
})
export class ClienteUpdateComponent implements OnInit {
  isSaving = false;

  userExtrasCollection: IUserExtra[] = [];

  editForm = this.fb.group({
    id: [],
    userExtra: [],
  });

  constructor(
    protected clienteService: ClienteService,
    protected userExtraService: UserExtraService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cliente }) => {
      this.updateForm(cliente);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cliente = this.createFromForm();
    if (cliente.id !== undefined) {
      this.subscribeToSaveResponse(this.clienteService.update(cliente));
    } else {
      this.subscribeToSaveResponse(this.clienteService.create(cliente));
    }
  }

  trackUserExtraById(index: number, item: IUserExtra): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICliente>>): void {
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

  protected updateForm(cliente: ICliente): void {
    this.editForm.patchValue({
      id: cliente.id,
      userExtra: cliente.userExtra,
    });

    this.userExtrasCollection = this.userExtraService.addUserExtraToCollectionIfMissing(this.userExtrasCollection, cliente.userExtra);
  }

  protected loadRelationshipsOptions(): void {
    this.userExtraService
      .query({ filter: 'cliente-is-null' })
      .pipe(map((res: HttpResponse<IUserExtra[]>) => res.body ?? []))
      .pipe(
        map((userExtras: IUserExtra[]) =>
          this.userExtraService.addUserExtraToCollectionIfMissing(userExtras, this.editForm.get('userExtra')!.value)
        )
      )
      .subscribe((userExtras: IUserExtra[]) => (this.userExtrasCollection = userExtras));
  }

  protected createFromForm(): ICliente {
    return {
      ...new Cliente(),
      id: this.editForm.get(['id'])!.value,
      userExtra: this.editForm.get(['userExtra'])!.value,
    };
  }
}
