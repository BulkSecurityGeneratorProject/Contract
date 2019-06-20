import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IContractType } from 'app/shared/model/contract-type.model';
import { AccountService } from 'app/core';
import { ContractTypeService } from './contract-type.service';

@Component({
  selector: 'jhi-contract-type',
  templateUrl: './contract-type.component.html'
})
export class ContractTypeComponent implements OnInit, OnDestroy {
  contractTypes: IContractType[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected contractTypeService: ContractTypeService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.contractTypeService
      .query()
      .pipe(
        filter((res: HttpResponse<IContractType[]>) => res.ok),
        map((res: HttpResponse<IContractType[]>) => res.body)
      )
      .subscribe(
        (res: IContractType[]) => {
          this.contractTypes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInContractTypes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IContractType) {
    return item.id;
  }

  registerChangeInContractTypes() {
    this.eventSubscriber = this.eventManager.subscribe('contractTypeListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
