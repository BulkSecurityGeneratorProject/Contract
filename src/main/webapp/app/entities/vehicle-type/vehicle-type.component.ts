import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IVehicleType } from 'app/shared/model/vehicle-type.model';
import { AccountService } from 'app/core';
import { VehicleTypeService } from './vehicle-type.service';

@Component({
  selector: 'jhi-vehicle-type',
  templateUrl: './vehicle-type.component.html'
})
export class VehicleTypeComponent implements OnInit, OnDestroy {
  vehicleTypes: IVehicleType[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected vehicleTypeService: VehicleTypeService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.vehicleTypeService
      .query()
      .pipe(
        filter((res: HttpResponse<IVehicleType[]>) => res.ok),
        map((res: HttpResponse<IVehicleType[]>) => res.body)
      )
      .subscribe(
        (res: IVehicleType[]) => {
          this.vehicleTypes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInVehicleTypes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IVehicleType) {
    return item.id;
  }

  registerChangeInVehicleTypes() {
    this.eventSubscriber = this.eventManager.subscribe('vehicleTypeListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
