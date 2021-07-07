import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IVenditore, getVenditoreIdentifier } from '../venditore.model';

export type EntityResponseType = HttpResponse<IVenditore>;
export type EntityArrayResponseType = HttpResponse<IVenditore[]>;

@Injectable({ providedIn: 'root' })
export class VenditoreService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/venditores');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(venditore: IVenditore): Observable<EntityResponseType> {
    return this.http.post<IVenditore>(this.resourceUrl, venditore, { observe: 'response' });
  }

  update(venditore: IVenditore): Observable<EntityResponseType> {
    return this.http.put<IVenditore>(`${this.resourceUrl}/${getVenditoreIdentifier(venditore) as number}`, venditore, {
      observe: 'response',
    });
  }

  partialUpdate(venditore: IVenditore): Observable<EntityResponseType> {
    return this.http.patch<IVenditore>(`${this.resourceUrl}/${getVenditoreIdentifier(venditore) as number}`, venditore, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVenditore>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVenditore[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addVenditoreToCollectionIfMissing(
    venditoreCollection: IVenditore[],
    ...venditoresToCheck: (IVenditore | null | undefined)[]
  ): IVenditore[] {
    const venditores: IVenditore[] = venditoresToCheck.filter(isPresent);
    if (venditores.length > 0) {
      const venditoreCollectionIdentifiers = venditoreCollection.map(venditoreItem => getVenditoreIdentifier(venditoreItem)!);
      const venditoresToAdd = venditores.filter(venditoreItem => {
        const venditoreIdentifier = getVenditoreIdentifier(venditoreItem);
        if (venditoreIdentifier == null || venditoreCollectionIdentifiers.includes(venditoreIdentifier)) {
          return false;
        }
        venditoreCollectionIdentifiers.push(venditoreIdentifier);
        return true;
      });
      return [...venditoresToAdd, ...venditoreCollection];
    }
    return venditoreCollection;
  }
}
