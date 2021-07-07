import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EntityArrayResponseType } from '../entities/prodotto/service/prodotto.service';
import { IProdotto } from '../entities/prodotto/prodotto.model';

@Injectable({
  providedIn: 'root'
})
export class VetrinaService {

  urlVetrina='api/prodottos'

  constructor(private http: HttpClient) { }

  ritornaVetrina(): Observable<EntityArrayResponseType> {
    return this.http.get<IProdotto[]>(this.urlVetrina, {observe: 'response'})
  }

}
