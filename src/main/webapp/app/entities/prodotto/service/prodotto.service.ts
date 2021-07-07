import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProdotto, getProdottoIdentifier } from '../prodotto.model';

export type EntityResponseType = HttpResponse<IProdotto>;
export type EntityArrayResponseType = HttpResponse<IProdotto[]>;

@Injectable({ providedIn: 'root' })
export class ProdottoService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/prodottos');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(prodotto: IProdotto): Observable<EntityResponseType> {
    return this.http.post<IProdotto>(this.resourceUrl, prodotto, { observe: 'response' });
  }

  update(prodotto: IProdotto): Observable<EntityResponseType> {
    return this.http.put<IProdotto>(`${this.resourceUrl}/${getProdottoIdentifier(prodotto) as number}`, prodotto, { observe: 'response' });
  }

  partialUpdate(prodotto: IProdotto): Observable<EntityResponseType> {
    return this.http.patch<IProdotto>(`${this.resourceUrl}/${getProdottoIdentifier(prodotto) as number}`, prodotto, {
      observe: 'response',
    });
  }

  find(id: number | undefined): Observable<EntityResponseType> {
    return this.http.get<IProdotto>(`${this.resourceUrl}/${id!}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProdotto[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addProdottoToCollectionIfMissing(prodottoCollection: IProdotto[], ...prodottosToCheck: (IProdotto | null | undefined)[]): IProdotto[] {
    const prodottos: IProdotto[] = prodottosToCheck.filter(isPresent);
    if (prodottos.length > 0) {
      const prodottoCollectionIdentifiers = prodottoCollection.map(prodottoItem => getProdottoIdentifier(prodottoItem)!);
      const prodottosToAdd = prodottos.filter(prodottoItem => {
        const prodottoIdentifier = getProdottoIdentifier(prodottoItem);
        if (prodottoIdentifier == null || prodottoCollectionIdentifiers.includes(prodottoIdentifier)) {
          return false;
        }
        prodottoCollectionIdentifiers.push(prodottoIdentifier);
        return true;
      });
      return [...prodottosToAdd, ...prodottoCollection];
    }
    return prodottoCollection;
  }
}
