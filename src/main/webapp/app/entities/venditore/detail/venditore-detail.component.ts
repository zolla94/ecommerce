import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVenditore } from '../venditore.model';

@Component({
  selector: 'jhi-venditore-detail',
  templateUrl: './venditore-detail.component.html',
})
export class VenditoreDetailComponent implements OnInit {
  venditore: IVenditore | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ venditore }) => {
      this.venditore = venditore;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
