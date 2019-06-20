import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ILicenseType } from 'app/shared/model/license-type.model';
import { AccountService } from 'app/core';
import { LicenseTypeService } from './license-type.service';

@Component({
  selector: 'jhi-license-type',
  templateUrl: './license-type.component.html'
})
export class LicenseTypeComponent implements OnInit, OnDestroy {
  licenseTypes: ILicenseType[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected licenseTypeService: LicenseTypeService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.licenseTypeService
      .query()
      .pipe(
        filter((res: HttpResponse<ILicenseType[]>) => res.ok),
        map((res: HttpResponse<ILicenseType[]>) => res.body)
      )
      .subscribe(
        (res: ILicenseType[]) => {
          this.licenseTypes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInLicenseTypes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ILicenseType) {
    return item.id;
  }

  registerChangeInLicenseTypes() {
    this.eventSubscriber = this.eventManager.subscribe('licenseTypeListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
