import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IVehicleItemStatus } from 'app/shared/model/vehicle-item-status.model';
import { AccountService } from 'app/core';
import { VehicleItemStatusService } from './vehicle-item-status.service';

@Component({
  selector: 'jhi-vehicle-item-status',
  templateUrl: './vehicle-item-status.component.html'
})
export class VehicleItemStatusComponent implements OnInit, OnDestroy {
  vehicleItemStatuses: IVehicleItemStatus[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected vehicleItemStatusService: VehicleItemStatusService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.vehicleItemStatusService
      .query()
      .pipe(
        filter((res: HttpResponse<IVehicleItemStatus[]>) => res.ok),
        map((res: HttpResponse<IVehicleItemStatus[]>) => res.body)
      )
      .subscribe(
        (res: IVehicleItemStatus[]) => {
          this.vehicleItemStatuses = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInVehicleItemStatuses();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IVehicleItemStatus) {
    return item.id;
  }

  registerChangeInVehicleItemStatuses() {
    this.eventSubscriber = this.eventManager.subscribe('vehicleItemStatusListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
