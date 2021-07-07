import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { UserExtraService } from '../../entities/user-extra/service/user-extra.service';
import { UserExtra } from '../../entities/user-extra/user-extra.model';

@Component({
  selector: 'jhi-settings',
  templateUrl: './settings.component.html',
})
export class SettingsComponent implements OnInit {
  account!: Account;
  userExtra!: UserExtra;
  success = false;
  mark = true;

  settingsForm = this.fb.group({
    firstName: [undefined, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
    lastName: [undefined, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
    indirizzo: [undefined, ''],
    telefono: [undefined, ''],
    email: [undefined, [Validators.required, Validators.minLength(5), Validators.maxLength(254), Validators.email]],
  });

  editForm = this.fb.group({
    id: [],
    indirizzo: [],
    telefono: [],
    ruolo: [],
    user: [],
  });

  constructor(private accountService: AccountService, private fb: FormBuilder, private userExtraService: UserExtraService) { }

  ngOnInit(): void {
    this.accountService.fetchCliente().subscribe(c => {
      this.accountService.identity().subscribe(account => {
        if (account) {
          this.settingsForm.patchValue({
            firstName: account.firstName,
            lastName: account.lastName,
            indirizzo: c.userExtra!.indirizzo,
            telefono: c.userExtra!.telefono,
            email: account.email,
          });

          this.userExtra = c.userExtra!;
          this.account = account;
        }
      });
    })
    this.accountService.fetchVenditore().subscribe(v => {
      this.userExtra = v.userExtra!;
      this.accountService.identity().subscribe(account => {
        if (account) {
          this.settingsForm.patchValue({
            firstName: account.firstName,
            lastName: account.lastName,
            indirizzo: v.userExtra!.indirizzo,
            telefono: v.userExtra!.telefono,
            email: account.email,
          });

          this.account = account;
        }
      });
    })
      this.accountService.identity().subscribe(account => {
        if (account) {
          this.settingsForm.patchValue({
            firstName: account.firstName,
            lastName: account.lastName,
            email: account.email,
          });
          this.account = account;
          if (this.account.login === 'zolla94') {
            this.mark = false;
          }
        }
      });
  }

  save(): void {
    this.success = false;

    this.account.firstName = this.settingsForm.get('firstName')!.value;
    this.account.lastName = this.settingsForm.get('lastName')!.value;
    if (this.mark === true) {
      this.userExtra.indirizzo = this.settingsForm.get('indirizzo')!.value;
      this.userExtra.telefono = this.settingsForm.get('telefono')!.value;
      this.userExtra.user = this.account;
    }
    this.account.email = this.settingsForm.get('email')!.value;

    this.accountService.save(this.account).subscribe(() => {
      this.success = true;

      this.accountService.authenticate(this.account);
    });
    this.userExtraService.update(this.userExtra).subscribe(() => {
      // alert(this.userExtra.indirizzo)
    })
  }
}
