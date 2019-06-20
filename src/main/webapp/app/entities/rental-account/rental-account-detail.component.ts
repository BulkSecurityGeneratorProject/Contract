import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRentalAccount } from 'app/shared/model/rental-account.model';

@Component({
  selector: 'jhi-rental-account-detail',
  templateUrl: './rental-account-detail.component.html'
})
export class RentalAccountDetailComponent implements OnInit {
  rentalAccount: IRentalAccount;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ rentalAccount }) => {
      this.rentalAccount = rentalAccount;
    });
  }

  previousState() {
    window.history.back();
  }
}
