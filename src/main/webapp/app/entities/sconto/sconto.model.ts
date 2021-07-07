export interface ISconto {
  id?: number;
  nome?: string | null;
  giorni?: string | null;
  valore?: number | null;
  cat?: string | null;
  attivo?: boolean | null;
}

export class Sconto implements ISconto {
  constructor(
    public id?: number,
    public nome?: string | null,
    public giorni?: string | null,
    public valore?: number | null,
    public cat?: string | null,
    public attivo?: boolean | null
  ) {
    this.attivo = this.attivo ?? false;
  }
}

export function getScontoIdentifier(sconto: ISconto): number | undefined {
  return sconto.id;
}
