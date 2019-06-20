import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ContractSharedModule } from 'app/shared';
import {
  VehicleTypeComponent,
  VehicleTypeDetailComponent,
  VehicleTypeUpdateComponent,
  VehicleTypeDeletePopupComponent,
  VehicleTypeDeleteDialogComponent,
  vehicleTypeRoute,
  vehicleTypePopupRoute
} from './';

const ENTITY_STATES = [...vehicleTypeRoute, ...vehicleTypePopupRoute];

@NgModule({
  imports: [ContractSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    VehicleTypeComponent,
    VehicleTypeDetailComponent,
    VehicleTypeUpdateComponent,
    VehicleTypeDeleteDialogComponent,
    VehicleTypeDeletePopupComponent
  ],
  entryComponents: [VehicleTypeComponent, VehicleTypeUpdateComponent, VehicleTypeDeleteDialogComponent, VehicleTypeDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ContractVehicleTypeModule {}
