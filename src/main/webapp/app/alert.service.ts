import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AlertService {

  urlAlert = 'api/getAlert'

  constructor(private http: HttpClient) { }

}
