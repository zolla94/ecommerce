import { IUserExtra } from 'app/entities/user-extra/user-extra.model';
import { IOrdine } from 'app/entities/ordine/ordine.model';

export interface ICliente {
  id?: number;
  userExtra?: IUserExtra | null;
  ordines?: IOrdine[] | null;
}

export class Cliente implements ICliente {
  constructor(public id?: number, public userExtra?: IUserExtra | null, public ordines?: IOrdine[] | null) {}
}

export function getClienteIdentifier(cliente: ICliente): number | undefined {
  return cliente.id;
}
