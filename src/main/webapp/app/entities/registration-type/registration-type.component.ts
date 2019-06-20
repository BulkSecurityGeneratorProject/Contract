import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRegistrationType } from 'app/shared/model/registration-type.model';
import { AccountService } from 'app/core';
import { RegistrationTypeService } from './registration-type.service';

@Component({
  selector: 'jhi-registration-type',
  templateUrl: './registration-type.component.html'
})
export class RegistrationTypeComponent implements OnInit, OnDestroy {
  registrationTypes: IRegistrationType[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected registrationTypeService: RegistrationTypeService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.registrationTypeService
      .query()
      .pipe(
        filter((res: HttpResponse<IRegistrationType[]>) => res.ok),
        map((res: HttpResponse<IRegistrationType[]>) => res.body)
      )
      .subscribe(
        (res: IRegistrationType[]) => {
          this.registrationTypes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInRegistrationTypes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IRegistrationType) {
    return item.id;
  }

  registerChangeInRegistrationTypes() {
    this.eventSubscriber = this.eventManager.subscribe('registrationTypeListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
