import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ContractSharedModule } from 'app/shared';
import {
  VehicleItemStatusComponent,
  VehicleItemStatusDetailComponent,
  VehicleItemStatusUpdateComponent,
  VehicleItemStatusDeletePopupComponent,
  VehicleItemStatusDeleteDialogComponent,
  vehicleItemStatusRoute,
  vehicleItemStatusPopupRoute
} from './';

const ENTITY_STATES = [...vehicleItemStatusRoute, ...vehicleItemStatusPopupRoute];

@NgModule({
  imports: [ContractSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    VehicleItemStatusComponent,
    VehicleItemStatusDetailComponent,
    VehicleItemStatusUpdateComponent,
    VehicleItemStatusDeleteDialogComponent,
    VehicleItemStatusDeletePopupComponent
  ],
  entryComponents: [
    VehicleItemStatusComponent,
    VehicleItemStatusUpdateComponent,
    VehicleItemStatusDeleteDialogComponent,
    VehicleItemStatusDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ContractVehicleItemStatusModule {}
