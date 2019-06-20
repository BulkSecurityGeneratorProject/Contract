import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IInsuranceType } from 'app/shared/model/insurance-type.model';
import { AccountService } from 'app/core';
import { InsuranceTypeService } from './insurance-type.service';

@Component({
  selector: 'jhi-insurance-type',
  templateUrl: './insurance-type.component.html'
})
export class InsuranceTypeComponent implements OnInit, OnDestroy {
  insuranceTypes: IInsuranceType[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected insuranceTypeService: InsuranceTypeService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.insuranceTypeService
      .query()
      .pipe(
        filter((res: HttpResponse<IInsuranceType[]>) => res.ok),
        map((res: HttpResponse<IInsuranceType[]>) => res.body)
      )
      .subscribe(
        (res: IInsuranceType[]) => {
          this.insuranceTypes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInInsuranceTypes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IInsuranceType) {
    return item.id;
  }

  registerChangeInInsuranceTypes() {
    this.eventSubscriber = this.eventManager.subscribe('insuranceTypeListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
