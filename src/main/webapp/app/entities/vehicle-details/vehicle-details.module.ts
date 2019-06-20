import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ContractSharedModule } from 'app/shared';
import {
  VehicleDetailsComponent,
  VehicleDetailsDetailComponent,
  VehicleDetailsUpdateComponent,
  VehicleDetailsDeletePopupComponent,
  VehicleDetailsDeleteDialogComponent,
  vehicleDetailsRoute,
  vehicleDetailsPopupRoute
} from './';

const ENTITY_STATES = [...vehicleDetailsRoute, ...vehicleDetailsPopupRoute];

@NgModule({
  imports: [ContractSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    VehicleDetailsComponent,
    VehicleDetailsDetailComponent,
    VehicleDetailsUpdateComponent,
    VehicleDetailsDeleteDialogComponent,
    VehicleDetailsDeletePopupComponent
  ],
  entryComponents: [
    VehicleDetailsComponent,
    VehicleDetailsUpdateComponent,
    VehicleDetailsDeleteDialogComponent,
    VehicleDetailsDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ContractVehicleDetailsModule {}
