import { IUserExtra } from 'app/entities/user-extra/user-extra.model';
import { IProdotto } from 'app/entities/prodotto/prodotto.model';
import { IOrdine } from 'app/entities/ordine/ordine.model';

export interface IVenditore {
  id?: number;
  userExtra?: IUserExtra | null;
  prodottos?: IProdotto[] | null;
  ordines?: IOrdine[] | null;
  indirizzo?: string | null;
}

export class Venditore implements IVenditore {
  constructor(
    public id?: number,
    public userExtra?: IUserExtra | null,
    public prodottos?: IProdotto[] | null,
    public ordines?: IOrdine[] | null,
    public indirizzo?: string | null
  ) {}
}

export function getVenditoreIdentifier(venditore: IVenditore): number | undefined {
  return venditore.id;
}
