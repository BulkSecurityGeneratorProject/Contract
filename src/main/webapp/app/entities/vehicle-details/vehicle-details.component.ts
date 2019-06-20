import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IVehicleDetails } from 'app/shared/model/vehicle-details.model';
import { AccountService } from 'app/core';
import { VehicleDetailsService } from './vehicle-details.service';

@Component({
  selector: 'jhi-vehicle-details',
  templateUrl: './vehicle-details.component.html'
})
export class VehicleDetailsComponent implements OnInit, OnDestroy {
  vehicleDetails: IVehicleDetails[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected vehicleDetailsService: VehicleDetailsService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.vehicleDetailsService
      .query()
      .pipe(
        filter((res: HttpResponse<IVehicleDetails[]>) => res.ok),
        map((res: HttpResponse<IVehicleDetails[]>) => res.body)
      )
      .subscribe(
        (res: IVehicleDetails[]) => {
          this.vehicleDetails = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInVehicleDetails();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IVehicleDetails) {
    return item.id;
  }

  registerChangeInVehicleDetails() {
    this.eventSubscriber = this.eventManager.subscribe('vehicleDetailsListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
