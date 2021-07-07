import { Component, OnInit } from '@angular/core';
import { ScontoService } from '../entities/sconto/service/sconto.service';

@Component({
  selector: 'jhi-sconti-settimanali',
  templateUrl: './sconti-settimanali.component.html',
  styleUrls: ['./sconti-settimanali.component.scss']
})
export class ScontiSettimanaliComponent implements OnInit {

  sconto!: any;
  valore!: number;

  constructor(public scontoService: ScontoService) {}

  ngOnInit(): void {
    this.scontoService.find(1).subscribe(c => {
      this.sconto = c.body;
      this.valore = this.sconto?.valore
    })
  }

}
