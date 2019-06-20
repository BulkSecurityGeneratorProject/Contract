import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContractType } from 'app/shared/model/contract-type.model';

@Component({
  selector: 'jhi-contract-type-detail',
  templateUrl: './contract-type-detail.component.html'
})
export class ContractTypeDetailComponent implements OnInit {
  contractType: IContractType;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ contractType }) => {
      this.contractType = contractType;
    });
  }

  previousState() {
    window.history.back();
  }
}
