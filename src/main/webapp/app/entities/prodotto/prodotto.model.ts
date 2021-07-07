import { IVenditore } from 'app/entities/venditore/venditore.model';
import { IOrdine } from 'app/entities/ordine/ordine.model';
import { Cat } from 'app/entities/enumerations/cat.model';

export interface IProdotto {
  id?: number;
  nome?: string;
  descrizione?: string;
  prezzo?: number;
  disponibilita?: number | null;
  categoria?: Cat;
  imageUrl?: string | null;
  venditore?: IVenditore | null;
  ordines?: IOrdine[] | null;
}

export class Prodotto implements IProdotto {
  constructor(
    public id?: number,
    public nome?: string,
    public descrizione?: string,
    public prezzo?: number,
    public disponibilita?: number | null,
    public categoria?: Cat,
    public imageUrl?: string | null,
    public venditore?: IVenditore | null,
    public ordines?: IOrdine[] | null
  ) {}
}

export function getProdottoIdentifier(prodotto: IProdotto): number | undefined {
  return prodotto.id;
}
