import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProdotto } from '../prodotto.model';

@Component({
  selector: 'jhi-prodotto-detail',
  templateUrl: './prodotto-detail.component.html',
})
export class ProdottoDetailComponent implements OnInit {
  prodotto: IProdotto | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ prodotto }) => {
      this.prodotto = prodotto;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
