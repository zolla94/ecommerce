import { ICliente } from 'app/entities/cliente/cliente.model';
import { IProdotto } from 'app/entities/prodotto/prodotto.model';
import { IVenditore } from 'app/entities/venditore/venditore.model';

export interface IOrdine {
  id?: number;
  acquistato?: boolean;
  spedito?: boolean | null;
  quantita?: number;
  totale?: number;
  cliente?: ICliente | null;
  prodotto?: IProdotto | null;
  venditore?: IVenditore | null;
}

export class Ordine implements IOrdine {
  constructor(
    public id?: number,
    public acquistato?: boolean,
    public spedito?: boolean | null,
    public quantita?: number,
    public totale?: number,
    public cliente?: ICliente | null,
    public prodotto?: IProdotto | null,
    public venditore?: IVenditore | null
  ) {
    this.acquistato = this.acquistato ?? false;
    this.spedito = this.spedito ?? false;
  }
}

export function getOrdineIdentifier(ordine: IOrdine): number | undefined {
  return ordine.id;
}
