import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IOrdine } from '../entities/ordine/ordine.model';
import { EntityArrayResponseType } from '../entities/ordine/service/ordine.service';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  urlCarrello = 'api/getCarrello'
  urlRiepilogo = 'api/getRiepilogo'

  constructor(private http: HttpClient) { }

  ritornaCarrello(): Observable<EntityArrayResponseType> {
    return this.http.get<IOrdine[]>(this.urlCarrello, {observe: 'response'});
  }

  ritornaRiepilogo(): Observable<EntityArrayResponseType> {
    return this.http.get<IOrdine[]>(this.urlRiepilogo, {observe: 'response'});
  }
}
