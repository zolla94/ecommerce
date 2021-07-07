import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';
import { OrdineDeleteDialogComponent } from '../../entities/ordine/delete/ordine-delete-dialog.component';
import { IOrdine, Ordine } from '../../entities/ordine/ordine.model';
import { OrdineService } from '../../entities/ordine/service/ordine.service';
import { IProdotto } from '../../entities/prodotto/prodotto.model';
import { ProdottoService } from '../../entities/prodotto/service/prodotto.service';
import { ClienteService } from '../cliente.service';
import { ConfermaAcquistoComponent } from '../conferma-acquisto/conferma-acquisto/conferma-acquisto.component';
import { PdfMakeWrapper, Txt } from 'pdfmake-wrapper';
import * as pdfFonts from "pdfmake/build/vfs_fonts";
import { AccountService } from '../../core/auth/account.service';
import * as XLSX from 'xlsx';

PdfMakeWrapper.setFonts(pdfFonts);

@Component({
  selector: 'jhi-riepilogo',
  templateUrl: './riepilogo.component.html',
  styleUrls: ['./riepilogo.component.scss']
})
export class RiepilogoComponent implements OnInit {

  fileName = 'EstrattoExcel.xlsx';
  disp!: number;
  tot!: number;
  idProd!: number;
  ordini?: IOrdine[];
  estratto?: IOrdine[];
  isLoading = false;
  isSaving = false;
  nome: string | null | undefined;
  cognome: string | null | undefined;
  mail: string | null | undefined;
  indirizzo: string | null | undefined;
  telefono: string | null | undefined;
  prodotto?: IProdotto[];
  amount = 0;

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

  // editFormProdotto = this.fb.group({
  //   id: [],
  //   nome: [null, [Validators.required]],
  //   descrizione: [null, [Validators.required]],
  //   prezzo: [null, [Validators.required]],
  //   disponibilita: [],
  //   categoria: [null, [Validators.required]],
  //   imageUrl: [],
  //   venditore: [],
  // });

  constructor(
    private ordineService: OrdineService,
    private router: Router,
    private prodottoService: ProdottoService,
    private accountService: AccountService,
    protected fb: FormBuilder,
    protected clienteService: ClienteService,
    protected activatedRoute: ActivatedRoute,
    protected modalService: NgbModal
  ) { }

  loadAll(): void {
    this.isLoading = true;

    this.clienteService.ritornaRiepilogo().subscribe(
      (res: HttpResponse<IOrdine[]>) => {
        this.isLoading = false;
        this.ordini = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    
    this.activatedRoute.data.subscribe(({ ordine }) => {
      this.activatedRoute.data.subscribe(({ prodotto }) => {
        this.disp = +this.activatedRoute.snapshot.paramMap.get('disp')!;
        this.tot = +this.activatedRoute.snapshot.paramMap.get('tot')!;
        this.updateForm(ordine, this.tot);
        prodotto.disponibilita = (this.disp - ordine.quantita!)
        this.save(ordine, this.disp, prodotto);
      })
    });
    this.loadAll();
  }
  
  printPDF(): void {
    const pdf = new PdfMakeWrapper();
    pdf.watermark('zollArt');
    this.accountService.identity().subscribe(account => {
      this.accountService.fetchCliente().subscribe(c => {
        this.indirizzo = c.userExtra!.indirizzo;
        this.telefono = c.userExtra!.telefono;
      })
      this.nome = account?.firstName;
      this.cognome = account?.lastName;
      this.mail = account?.email;
    })
    this.clienteService.ritornaRiepilogo().subscribe(
      (res: HttpResponse<IOrdine[]>) => {
        this.isLoading = false;
        this.estratto = res.body ?? [];
      })
    pdf.header(new Txt('Estratto conto').bold().italics().end);
    pdf.add(this.nome!.toString() + ' ' + this.cognome!.toString() 
            + pdf.ln(1) + this.mail!.toString() 
            + pdf.ln(1) + this.indirizzo!.toString() 
            + pdf.ln(1) + this.telefono!.toString()
            + pdf.ln(3)
            )
    this.estratto?.forEach(e => {
      pdf.add(e.prodotto?.nome)
      this.amount = this.amount + e.totale!;
    });
    pdf.add(new Txt('Totale:' + ' ' + this.amount.toString() + ' ' + '€').bold().italics().end)
    pdf.footer('zollArt s.r.l. - C.da Mito, Tricase (LE)')
    pdf.create().open();
  }

  printEXCEL(): void {
    const element = document.getElementById('excel-table'); 
    const ws: XLSX.WorkSheet =XLSX.utils.table_to_sheet(element);

    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');

    XLSX.writeFile(wb, this.fileName);
  }

  previousState(): void {
    window.history.back();
  }
  
  save(ordineQ: IOrdine, disp: number, prodotto: IProdotto): void {
    if ((disp - ordineQ.quantita!) >= 0) {
      this.isSaving = true;
      const ordine = this.createFromForm();
      this.subscribeToSaveResponse(this.ordineService.update(ordine));
      this.subscribeToSaveResponseProdotto(this.prodottoService.update(prodotto));
      this.open();
    } else {
      alert("Attenzione! Quantità non disponibile!");
      this.router.navigate(['carrello']);
    }
  }

  open(): void {
    this.modalService.open(ConfermaAcquistoComponent, { size: 'lg', backdrop: 'static' });
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
        this.loadAll();
      }
    });
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrdine>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected subscribeToSaveResponseProdotto(result: Observable<HttpResponse<IProdotto>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected updateForm(ordine: IOrdine, tot: number): void {
    this.editForm.patchValue({
      id: ordine.id,
      acquistato: true,
      spedito: ordine.spedito,
      quantita: ordine.quantita,
      totale: tot,
      cliente: ordine.cliente,
      prodotto: ordine.prodotto,
      venditore: ordine.venditore,
    });
  }

  protected createFromForm(): IOrdine {
    return {
      ...new Ordine(),
      id: this.editForm.get(['id'])!.value,
      acquistato: this.editForm.get(['acquistato'])!.value,
      spedito: this.editForm.get(['spedito'])!.value,
      quantita: this.editForm.get(['quantita'])!.value,
      totale: this.editForm.get(['totale'])!.value,
      cliente: this.editForm.get(['cliente'])!.value,
      prodotto: this.editForm.get(['prodotto'])!.value,
      venditore: this.editForm.get(['venditore'])!.value,
    };
  }

}
