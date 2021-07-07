import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-conferma-acquisto',
  templateUrl: './conferma-acquisto.component.html',
  styleUrls: ['./conferma-acquisto.component.scss']
})
export class ConfermaAcquistoComponent {

  constructor(public activeModal: NgbActiveModal, public route: Router) { }

  ok(): void {
    this.activeModal.dismiss();
    this.route.navigate(['vetrina'])
  }

}
