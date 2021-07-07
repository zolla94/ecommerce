import { Pipe, PipeTransform } from '@angular/core';
import { ScontoService } from '../entities/sconto/service/sconto.service';

@Pipe({
  name: 'scontiSettimanali'
})
export class ScontiSettimanaliPipe implements PipeTransform {

  date: Date = new Date();
  cat1 = 'ACTIONFIGURE';
  cat2 = 'ARREDAMENTO';
  cat3 = 'ALTRO';
  sconto10 = 10;
  sconto20 = 20;
  sconto30 = 30;
  sconto40 = 40;
  sconto50 = 50;

  sconto!: any
  flag = true;

  constructor(public scontoService: ScontoService) {}
  
  transform(prodotto: number, cat: string): number {

    // return prodotto - (this.sconto50 / 100) * prodotto;

    if (cat === this.cat1 && (this.date.getDay() === 6 || this.date.getDay() === 7)) {
      return prodotto - (this.sconto50 / 100) * prodotto;
    } else if (cat === this.cat2 && (this.date.getDay() === 4 || this.date.getDay() === 5)) {
      return prodotto - (this.sconto40 / 100) * prodotto;
    } else if (cat === this.cat3 && (this.date.getDay() === 2 || this.date.getDay() === 3)) {
      return prodotto - (this.sconto30 / 100) * prodotto;
    } else {
      return prodotto;
    }
  }
}