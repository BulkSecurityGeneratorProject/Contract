import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPaymentDetails } from 'app/shared/model/payment-details.model';
import { AccountService } from 'app/core';
import { PaymentDetailsService } from './payment-details.service';

@Component({
  selector: 'jhi-payment-details',
  templateUrl: './payment-details.component.html'
})
export class PaymentDetailsComponent implements OnInit, OnDestroy {
  paymentDetails: IPaymentDetails[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected paymentDetailsService: PaymentDetailsService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.paymentDetailsService
      .query()
      .pipe(
        filter((res: HttpResponse<IPaymentDetails[]>) => res.ok),
        map((res: HttpResponse<IPaymentDetails[]>) => res.body)
      )
      .subscribe(
        (res: IPaymentDetails[]) => {
          this.paymentDetails = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPaymentDetails();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPaymentDetails) {
    return item.id;
  }

  registerChangeInPaymentDetails() {
    this.eventSubscriber = this.eventManager.subscribe('paymentDetailsListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
