import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISconto, getScontoIdentifier } from '../sconto.model';

export type EntityResponseType = HttpResponse<ISconto>;
export type EntityArrayResponseType = HttpResponse<ISconto[]>;

@Injectable({ providedIn: 'root' })
export class ScontoService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/scontos');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(sconto: ISconto): Observable<EntityResponseType> {
    return this.http.post<ISconto>(this.resourceUrl, sconto, { observe: 'response' });
  }

  update(sconto: ISconto): Observable<EntityResponseType> {
    return this.http.put<ISconto>(`${this.resourceUrl}/${getScontoIdentifier(sconto) as number}`, sconto, { observe: 'response' });
  }

  partialUpdate(sconto: ISconto): Observable<EntityResponseType> {
    return this.http.patch<ISconto>(`${this.resourceUrl}/${getScontoIdentifier(sconto) as number}`, sconto, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISconto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISconto[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addScontoToCollectionIfMissing(scontoCollection: ISconto[], ...scontosToCheck: (ISconto | null | undefined)[]): ISconto[] {
    const scontos: ISconto[] = scontosToCheck.filter(isPresent);
    if (scontos.length > 0) {
      const scontoCollectionIdentifiers = scontoCollection.map(scontoItem => getScontoIdentifier(scontoItem)!);
      const scontosToAdd = scontos.filter(scontoItem => {
        const scontoIdentifier = getScontoIdentifier(scontoItem);
        if (scontoIdentifier == null || scontoCollectionIdentifiers.includes(scontoIdentifier)) {
          return false;
        }
        scontoCollectionIdentifiers.push(scontoIdentifier);
        return true;
      });
      return [...scontosToAdd, ...scontoCollection];
    }
    return scontoCollection;
  }
}
