import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { IProdotto } from '../entities/prodotto/prodotto.model';
import { EntityArrayResponseType } from '../entities/prodotto/service/prodotto.service';
import { IOrdine } from '../entities/ordine/ordine.model';

@Injectable({
  providedIn: 'root'
})
export class VenditoreService {

  // url = "https://api.openweathermap.org/data/2.5/weather?q=Turin&appid=cfa22017cd8b63ad3291d52b5dc25062";
  urlProd = 'api/allProds'
  urlOrdini = 'api/allOrdini'
  // urlSpedito = 'api/spedito/4'

  constructor(private http: HttpClient) { }

  tuttiProdotti(): Observable<EntityArrayResponseType> {
    return this.http.get<IProdotto[]>(this.urlProd, {observe: 'response'});
  }

  tuttiOrdini(): Observable<EntityArrayResponseType> {
    return this.http.get<IOrdine[]>(this.urlOrdini, {observe: 'response'});
  }
}
