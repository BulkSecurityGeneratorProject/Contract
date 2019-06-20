import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVehicleDetails } from 'app/shared/model/vehicle-details.model';

@Component({
  selector: 'jhi-vehicle-details-detail',
  templateUrl: './vehicle-details-detail.component.html'
})
export class VehicleDetailsDetailComponent implements OnInit {
  vehicleDetails: IVehicleDetails;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ vehicleDetails }) => {
      this.vehicleDetails = vehicleDetails;
    });
  }

  previousState() {
    window.history.back();
  }
}
