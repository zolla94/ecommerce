import { IUserExtra } from "../../entities/user-extra/user-extra.model";

export class Account {
  constructor(
    public activated: boolean,
    public authorities: string[],
    public id: number,
    public email: string,
    public firstName: string | null,
    public langKey: string,
    public lastName: string | null,
    public login: string,
    public imageUrl: string | null
  ) { }
}

export class AccountVenditore {
  constructor(
    public id: number | undefined,
    public indirizzo: string | null,
    public userExtra: IUserExtra | null
  ) { }
}

export class AccountCliente {
  constructor(
    public id: number | undefined,
    public indirizzo: string | null,
    public userExtra: IUserExtra | null
  ) { }
}
