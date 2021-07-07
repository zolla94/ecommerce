import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AlertService } from '../alert.service';
import { AccountService } from '../core/auth/account.service';
import { IProdotto } from '../entities/prodotto/prodotto.model';
import { ScontoService } from '../entities/sconto/service/sconto.service';
import { AggiuntaCarrelloComponent } from './aggiunta-carrello/aggiunta-carrello/aggiunta-carrello.component';
import { VetrinaService } from './vetrina.service';

@Component({
  selector: 'jhi-vetrina',
  templateUrl: './vetrina.component.html',
  // templateName: '<td>{{ prodotto.prezzo | number: "1.1-2" }} €</td>',
  styleUrls: ['./vetrina.component.scss'],
})
export class VetrinaComponent implements OnInit {

  title = 'Prodotti';
  searchText = '';
  searchTexti = '';
  quant?: number;
  prodotti!: IProdotto[];
  isLoading = false;
  sconto!: any;
  valore!: number;
  cat!: string;
  day = 0;
  date: Date = new Date();
  flag: boolean[] = [];
  state: number[] = [];

  constructor(
    private vetrinaService: VetrinaService,
    private modalService: NgbModal,
    private accountService: AccountService,
    private route: Router,
    private scontoService: ScontoService,
    private alertService: AlertService,
  ) { }

  ngOnInit(): void {
    this.vetrina();
  }

  setQuant(q: string): number {
    this.quant = parseInt(q, 10);
    return this.quant;
  }

  vetrina(): void {
    this.vetrinaService.ritornaVetrina().subscribe(p => {
      this.isLoading = false;
      this.prodotti = p.body ?? [];
      this.mostraSconti(this.prodotti);
    },
    () => {
      this.isLoading = false;
    })
  }
  
  mostraSconti(prodotti: IProdotto[]): void {
    this.scontoService.find(1).subscribe(c => {
      this.sconto = c.body;
      this.valore = this.sconto?.valore / 100;
      this.cat = this.sconto?.cat;
      this.day = this.sconto?.giorni
      
      if (this.date.getDay() === +this.day) {
        prodotti.forEach(e => {
          // (document.getElementById(String(e.id)) as HTMLHtmlElement).setAttribute('style', 'color: red');
          if (e.categoria === this.cat) {
            e.prezzo = Number(e.prezzo!) * this.valore;
            this.flag[e.id!] = true;
          } else if (this.cat === 'TUTTE') {
            e.prezzo = Number(e.prezzo!) * this.valore;
            this.flag[e.id!] = true;
          } else {
            this.flag[e.id!] = false;
          }
        });
      }
    });
  }

  alertMe(prodotto: IProdotto): void {
    this.accountService.fetchCliente().subscribe(c => {
      this.state[prodotto.id!] = c.id!
    })
  }

  trackId(index: number, item: IProdotto): number {
    return item.id!;
  }

  open(): void {
    if (this.accountService.isAuthenticated() === false) {
      this.route.navigate(['login'])
    } else {
      this.modalService.open(AggiuntaCarrelloComponent, { size: 'lg', backdrop: 'static' });
    }
  }
}