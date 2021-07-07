import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';
import { AccountCliente } from '../../core/auth/account.model';
import { AccountService } from '../../core/auth/account.service';
import { OrdineDeleteDialogComponent } from '../../entities/ordine/delete/ordine-delete-dialog.component';
import { IOrdine, Ordine } from '../../entities/ordine/ordine.model';
import { OrdineService } from '../../entities/ordine/service/ordine.service';
import { IProdotto } from '../../entities/prodotto/prodotto.model';
import { ProdottoService } from '../../entities/prodotto/service/prodotto.service';
import { ScontoService } from '../../entities/sconto/service/sconto.service';
import { ClienteService } from '../cliente.service';

@Component({
  selector: 'jhi-carrello',
  templateUrl: './carrello.component.html',
  template: '',
  styleUrls: ['./carrello.component.scss']
})
export class CarrelloComponent implements OnInit {

  ordini?: IOrdine[];
  cliente!: AccountCliente[];
  prodotto?: IProdotto;
  prod!: IProdotto;
  isLoading = false;
  isSaving = false;
  q!: number;
  tot!: number;
  check: boolean[] = []
  ordineId: any
  prodId: any
  totS: any
  disp: any
  quantita: any
  nome: any
  dispR: number[] = [];
  sconto!: any;
  valore!: number;
  cat!: string;
  day = 0;
  date: Date = new Date();
  flag: boolean[] = [];


  editForm = this.fb.group({
    id: [],
    acquistato: [null, [Validators.required]],
    spedito: [],
    quantita: [null, [Validators.required]],
    totale: [],
    cliente: [],
    prodotto: [],
    venditore: [],
  });

  constructor(
    private scontoService: ScontoService,
    private accountService: AccountService,
    private ordineService: OrdineService,
    private prodottoService: ProdottoService,
    private router: Router,
    protected clienteService: ClienteService,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) { }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ prodotto }) => {
      this.q = +this.activatedRoute.snapshot.paramMap.get('quant')!;
      this.tot = +this.activatedRoute.snapshot.paramMap.get('tot')!;
      this.save(prodotto, this.q, this.tot);
    });
    this.vediCarrello();
  }

  mostraSconti(prodotti: IOrdine[]): void {
    this.scontoService.find(1).subscribe(c => {
      this.sconto = c.body;
      this.valore = this.sconto?.valore / 100
      this.cat = this.sconto?.cat;
      this.day = this.sconto?.giorni
      if (this.date.getDay() === +this.day) {
        prodotti.forEach(e => {
          if (e.prodotto?.categoria === this.cat) {
            e.prodotto.prezzo = Number(e.prodotto.prezzo!) * this.valore
            this.flag[e.id!] = true;
          } else if (this.cat === 'TUTTE') {
            e.prodotto!.prezzo = Number(e.prodotto!.prezzo!) * this.valore
            this.flag[e.id!] = true;
          } else {
            this.flag[e.id!] = false;
          }
        })
      }
    });
  }

  getSelected(): void {
    this.ordineId = this.ordini!.filter((ordine, index) => this.check[index]).map(ordine => ordine.id)
    this.prodId = this.ordini!.filter((ordine, index) => this.check[index]).map(ordine => ordine.prodotto?.id)
    this.disp = this.ordini!.filter((ordine, index) => this.check[index]).map(ordine => ordine.prodotto?.disponibilita)
    this.totS = this.ordini!.filter((ordine, index) => this.check[index])
      .map((ordine, index) => document.getElementById(this.ordineId[index])!.title)
    this.quantita = this.ordini!.filter((ordine, index) => this.check[index]).map(ordine => ordine.quantita)
    this.nome = this.ordini!.filter((ordine, index) => this.check[index]).map(ordine => ordine.prodotto?.nome)

    for (let i = 0; i < this.ordineId.length; i++) {
      const dispR = this.disp[i] - this.quantita[i];
      if (dispR >= 0) {
        this.ordineService.multiplo(this.ordineId[i], this.prodId[i], dispR, this.totS[i]).subscribe();
      } else {
        alert('Quantita non disponibile per ' + String(this.nome[i]))
      }

    }

    window.location.reload();
  }

  vediCarrello(): void {
    this.isLoading = true;
    this.clienteService.ritornaCarrello().subscribe(
      (res: HttpResponse<IOrdine[]>) => {
        this.isLoading = false;
        this.ordini = res.body ?? [];
        this.mostraSconti(this.ordini);
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  save(prodotto: IProdotto, q: number | undefined, tot: number | undefined): void {
    this.isSaving = true;
    this.accountService.fetchCliente().subscribe(c => {
      const ordine = this.createFromForm(prodotto, c, q, tot);
      if (ordine.id !== undefined) {
        this.subscribeToSaveResponse(this.ordineService.update(ordine));

      } else {
        this.subscribeToSaveResponse(this.ordineService.create(ordine));
      }
    })
  }

  trackId(index: number, item: IOrdine): number {
    return item.id!;
  }

  delete(ordine: IOrdine): void {
    const modalRef = this.modalService.open(OrdineDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.ordine = ordine;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.vediCarrello();
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrdine>>): void {
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

  protected createFromForm(p: IProdotto, c: AccountCliente, q: number | undefined, tot: number | undefined): IOrdine {
    return {
      ...new Ordine(),
      id: undefined,
      acquistato: false,
      spedito: false,
      quantita: q,
      totale: tot! * q!,
      cliente: c,
      prodotto: p,
      venditore: p.venditore,
    };
  }

}
