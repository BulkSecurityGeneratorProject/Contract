import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVehicleItemStatus } from 'app/shared/model/vehicle-item-status.model';

@Component({
  selector: 'jhi-vehicle-item-status-detail',
  templateUrl: './vehicle-item-status-detail.component.html'
})
export class VehicleItemStatusDetailComponent implements OnInit {
  vehicleItemStatus: IVehicleItemStatus;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ vehicleItemStatus }) => {
      this.vehicleItemStatus = vehicleItemStatus;
    });
  }

  previousState() {
    window.history.back();
  }
}
