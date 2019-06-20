import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ContractSharedModule } from 'app/shared';
import {
  FuelTypeComponent,
  FuelTypeDetailComponent,
  FuelTypeUpdateComponent,
  FuelTypeDeletePopupComponent,
  FuelTypeDeleteDialogComponent,
  fuelTypeRoute,
  fuelTypePopupRoute
} from './';

const ENTITY_STATES = [...fuelTypeRoute, ...fuelTypePopupRoute];

@NgModule({
  imports: [ContractSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    FuelTypeComponent,
    FuelTypeDetailComponent,
    FuelTypeUpdateComponent,
    FuelTypeDeleteDialogComponent,
    FuelTypeDeletePopupComponent
  ],
  entryComponents: [FuelTypeComponent, FuelTypeUpdateComponent, FuelTypeDeleteDialogComponent, FuelTypeDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ContractFuelTypeModule {}
