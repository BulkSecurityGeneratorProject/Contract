import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRentContract } from 'app/shared/model/rent-contract.model';

@Component({
  selector: 'jhi-rent-contract-detail',
  templateUrl: './rent-contract-detail.component.html'
})
export class RentContractDetailComponent implements OnInit {
  rentContract: IRentContract;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ rentContract }) => {
      this.rentContract = rentContract;
    });
  }

  previousState() {
    window.history.back();
  }
}
