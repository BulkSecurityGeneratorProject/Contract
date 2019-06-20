import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ContractSharedModule } from 'app/shared';
import {
  RentalAccountComponent,
  RentalAccountDetailComponent,
  RentalAccountUpdateComponent,
  RentalAccountDeletePopupComponent,
  RentalAccountDeleteDialogComponent,
  rentalAccountRoute,
  rentalAccountPopupRoute
} from './';

const ENTITY_STATES = [...rentalAccountRoute, ...rentalAccountPopupRoute];

@NgModule({
  imports: [ContractSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    RentalAccountComponent,
    RentalAccountDetailComponent,
    RentalAccountUpdateComponent,
    RentalAccountDeleteDialogComponent,
    RentalAccountDeletePopupComponent
  ],
  entryComponents: [
    RentalAccountComponent,
    RentalAccountUpdateComponent,
    RentalAccountDeleteDialogComponent,
    RentalAccountDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ContractRentalAccountModule {}
