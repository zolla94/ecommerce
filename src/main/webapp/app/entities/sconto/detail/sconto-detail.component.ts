import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISconto } from '../sconto.model';

@Component({
  selector: 'jhi-sconto-detail',
  templateUrl: './sconto-detail.component.html',
})
export class ScontoDetailComponent implements OnInit {
  sconto: ISconto | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sconto }) => {
      this.sconto = sconto;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
