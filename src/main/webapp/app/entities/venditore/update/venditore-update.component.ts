import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IVenditore, Venditore } from '../venditore.model';
import { VenditoreService } from '../service/venditore.service';
import { IUserExtra } from 'app/entities/user-extra/user-extra.model';
import { UserExtraService } from 'app/entities/user-extra/service/user-extra.service';

@Component({
  selector: 'jhi-venditore-update',
  templateUrl: './venditore-update.component.html',
})
export class VenditoreUpdateComponent implements OnInit {
  isSaving = false;

  userExtrasCollection: IUserExtra[] = [];

  editForm = this.fb.group({
    id: [],
    userExtra: [],
  });

  constructor(
    protected venditoreService: VenditoreService,
    protected userExtraService: UserExtraService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ venditore }) => {
      this.updateForm(venditore);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const venditore = this.createFromForm();
    if (venditore.id !== undefined) {
      this.subscribeToSaveResponse(this.venditoreService.update(venditore));
    } else {
      this.subscribeToSaveResponse(this.venditoreService.create(venditore));
    }
  }

  trackUserExtraById(index: number, item: IUserExtra): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVenditore>>): void {
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

  protected updateForm(venditore: IVenditore): void {
    this.editForm.patchValue({
      id: venditore.id,
      userExtra: venditore.userExtra,
    });

    this.userExtrasCollection = this.userExtraService.addUserExtraToCollectionIfMissing(this.userExtrasCollection, venditore.userExtra);
  }

  protected loadRelationshipsOptions(): void {
    this.userExtraService
      .query({ filter: 'venditore-is-null' })
      .pipe(map((res: HttpResponse<IUserExtra[]>) => res.body ?? []))
      .pipe(
        map((userExtras: IUserExtra[]) =>
          this.userExtraService.addUserExtraToCollectionIfMissing(userExtras, this.editForm.get('userExtra')!.value)
        )
      )
      .subscribe((userExtras: IUserExtra[]) => (this.userExtrasCollection = userExtras));
  }

  protected createFromForm(): IVenditore {
    return {
      ...new Venditore(),
      id: this.editForm.get(['id'])!.value,
      userExtra: this.editForm.get(['userExtra'])!.value,
    };
  }
}
