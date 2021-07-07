import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { PdfMakeWrapper, Txt } from 'pdfmake-wrapper';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';
import { AccountService } from '../../core/auth/account.service';
import { ICliente } from '../../entities/cliente/cliente.model';
import { ClienteService } from '../../entities/cliente/service/cliente.service';
import { IOrdine, Ordine } from '../../entities/ordine/ordine.model';
import { OrdineService } from '../../entities/ordine/service/ordine.service';
import { UserExtraService } from '../../entities/user-extra/service/user-extra.service';
import { IUserExtra } from '../../entities/user-extra/user-extra.model';
import { VenditoreService } from '../venditore.service';
import * as pdfFonts from "pdfmake/build/vfs_fonts";
import * as XLSX from 'xlsx';

PdfMakeWrapper.setFonts(pdfFonts);

@Component({
  selector: 'jhi-ordini',
  templateUrl: './ordini.component.html',
  styleUrls: ['./ordini.component.scss'],
  // changeDetection: ChangeDetectionStrategy.OnPush
})
export class OrdiniComponent implements OnInit {

  ordine?: IOrdine[];
  isLoading = false;
  isSaving = false;
  cliente?: ICliente | null;
  numberOfTicks = 0;
  userE?: IUserExtra | null;
  uID?: number;
  estratto?: IOrdine[];
  nome: string | null | undefined;
  cognome: string | null | undefined;
  mail: string | null | undefined;
  indirizzo: string | null | undefined;
  telefono: string | null | undefined;
  amount = 0;
  fileName = 'EstrattoExcel.xlsx';

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
    private venditoreService: VenditoreService,
    private clienteService: ClienteService,
    private userExtraService: UserExtraService,
    private accountService: AccountService,
    protected ordineService: OrdineService,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {
    // setInterval(() => {
    //   this.numberOfTicks++;
    //   // require view to be updated
    //   this.ref.markForCheck();
    // }, 1000);
  }

  printPDF(): void {
    const pdf = new PdfMakeWrapper();
    pdf.watermark('zollArt');
    this.accountService.identity().subscribe(account => {
      this.accountService.fetchVenditore().subscribe(c => {
        this.indirizzo = c.userExtra!.indirizzo;
        this.telefono = c.userExtra!.telefono;
      })
      this.nome = account?.firstName;
      this.cognome = account?.lastName;
      this.mail = account?.email;
    })
    this.venditoreService.tuttiOrdini().subscribe(
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

  ngOnInit(): void {

    this.activatedRoute.data.subscribe(({ ordine }) => {
      this.updateForm(ordine);
      this.save();
    });
    this.viewOrdini();
  }
  
  wrapper(cliente: number): void {
    this.clienteService.find(cliente).subscribe(c => {
      this.cliente = c.body;
      this.uID = this.cliente?.userExtra?.id;
      this.userExtraService.find(this.uID!).subscribe(u => {
        this.userE = u.body;
      })
    })
  }

  // getAttributiUserExtra(id: number): ICliente {
  //   this.clienteService.find(id).subscribe(c => {
  //     this.cliente = c.body;
  //   })
  //   return this.cliente!;
  // }
  
  // getAttributiUser(id: number): IUserExtra {
  //   this.userExtraService.find(id).subscribe(u => {
  //     this.userE = u.body;
  //   })
  //   return this.userE!;
  // } 
  
  // getAttributiUserExtraVoid(id: number): void {
  //     this.clienteService.find(id).subscribe(c => {
  //       this.cliente = c.body;
  //     })
  //   }

  // getAttributiUserVoid(id: number): void {
  //   this.userExtraService.find(id).subscribe(u => {
  //     this.userE = u.body;
  //   })
  // } 


  viewOrdini(): void {
    this.isLoading = true;
    this.venditoreService.tuttiOrdini().subscribe(ordini => {
      this.isLoading = false;
      this.ordine = ordini.body ?? [];
    },
      () => {
        this.isLoading = false;
      })
  }

  previousState(): void {
    window.history.back();
  }

  trackId(index: number, item: IOrdine): number {
    return item.id!;
  }

  save(): void {
    this.isSaving = true;
    const ordine = this.createFromForm();
    if (ordine.id !== undefined) {
      this.subscribeToSaveResponse(this.ordineService.update(ordine));
    } else {
      this.subscribeToSaveResponse(this.ordineService.create(ordine));
    }
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

  protected updateForm(ordine: IOrdine): void {
    this.editForm.patchValue({
      id: ordine.id,
      acquistato: ordine.acquistato,
      spedito: true,
      quantita: ordine.quantita,
      totale: ordine.totale,
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