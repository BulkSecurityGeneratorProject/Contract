import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFuelType } from 'app/shared/model/fuel-type.model';

@Component({
  selector: 'jhi-fuel-type-detail',
  templateUrl: './fuel-type-detail.component.html'
})
export class FuelTypeDetailComponent implements OnInit {
  fuelType: IFuelType;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ fuelType }) => {
      this.fuelType = fuelType;
    });
  }

  previousState() {
    window.history.back();
  }
}
