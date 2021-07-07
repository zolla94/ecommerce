import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  authSubscription?: Subscription;
  clienteId!: number;

  constructor(private accountService: AccountService, private router: Router) {}

  ngOnInit(): void {
    this.authSubscription = this.accountService.getAuthenticationState().subscribe(account => (this.account = account));
  }

  checkNotifies(): void {
    this.accountService.fetchCliente().subscribe(c => {
      this.clienteId = c.id!;
    })
    /*
    1) Prendo id del cliente loggato
    2) Mando l'id al service e mi faccio tornare (nel backend) tutti gli avvisi prenotati
    3) Controllo se la disp dei prodotti in lista è diversa da zero (da backend)
    4) Se diversa da 0, mostro notifica ed elimino quel record dalla tabella
    */
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  ngOnDestroy(): void {
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
  }
}
