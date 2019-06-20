import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IFuelType } from 'app/shared/model/fuel-type.model';
import { AccountService } from 'app/core';
import { FuelTypeService } from './fuel-type.service';

@Component({
  selector: 'jhi-fuel-type',
  templateUrl: './fuel-type.component.html'
})
export class FuelTypeComponent implements OnInit, OnDestroy {
  fuelTypes: IFuelType[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected fuelTypeService: FuelTypeService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.fuelTypeService
      .query()
      .pipe(
        filter((res: HttpResponse<IFuelType[]>) => res.ok),
        map((res: HttpResponse<IFuelType[]>) => res.body)
      )
      .subscribe(
        (res: IFuelType[]) => {
          this.fuelTypes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInFuelTypes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IFuelType) {
    return item.id;
  }

  registerChangeInFuelTypes() {
    this.eventSubscriber = this.eventManager.subscribe('fuelTypeListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
