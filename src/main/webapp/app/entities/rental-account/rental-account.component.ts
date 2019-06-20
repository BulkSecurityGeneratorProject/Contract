import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRentalAccount } from 'app/shared/model/rental-account.model';
import { AccountService } from 'app/core';
import { RentalAccountService } from './rental-account.service';

@Component({
  selector: 'jhi-rental-account',
  templateUrl: './rental-account.component.html'
})
export class RentalAccountComponent implements OnInit, OnDestroy {
  rentalAccounts: IRentalAccount[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected rentalAccountService: RentalAccountService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.rentalAccountService
      .query()
      .pipe(
        filter((res: HttpResponse<IRentalAccount[]>) => res.ok),
        map((res: HttpResponse<IRentalAccount[]>) => res.body)
      )
      .subscribe(
        (res: IRentalAccount[]) => {
          this.rentalAccounts = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInRentalAccounts();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IRentalAccount) {
    return item.id;
  }

  registerChangeInRentalAccounts() {
    this.eventSubscriber = this.eventManager.subscribe('rentalAccountListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
