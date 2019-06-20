import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContractStatus } from 'app/shared/model/contract-status.model';

@Component({
  selector: 'jhi-contract-status-detail',
  templateUrl: './contract-status-detail.component.html'
})
export class ContractStatusDetailComponent implements OnInit {
  contractStatus: IContractStatus;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ contractStatus }) => {
      this.contractStatus = contractStatus;
    });
  }

  previousState() {
    window.history.back();
  }
}
