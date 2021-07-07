import { IUser } from 'app/entities/user/user.model';
import { ICliente } from 'app/entities/cliente/cliente.model';
import { IVenditore } from 'app/entities/venditore/venditore.model';
import { Role } from 'app/entities/enumerations/role.model';

export interface IUserExtra {
  id?: number;
  indirizzo?: string | null;
  telefono?: string | null;
  ruolo?: Role | null;
  user?: IUser | null;
  cliente?: ICliente | null;
  venditore?: IVenditore | null;
}

export class UserExtra implements IUserExtra {
  constructor(
    public id?: number,
    public indirizzo?: string | null,
    public telefono?: string | null,
    public ruolo?: Role | null,
    public user?: IUser | null,
    public cliente?: ICliente | null,
    public venditore?: IVenditore | null
  ) {}
}

export function getUserExtraIdentifier(userExtra: IUserExtra): number | undefined {
  return userExtra.id;
}
