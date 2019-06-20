import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ContractSharedModule } from 'app/shared';
import {
  RentContractComponent,
  RentContractDetailComponent,
  RentContractUpdateComponent,
  RentContractDeletePopupComponent,
  RentContractDeleteDialogComponent,
  rentContractRoute,
  rentContractPopupRoute
} from './';

const ENTITY_STATES = [...rentContractRoute, ...rentContractPopupRoute];

@NgModule({
  imports: [ContractSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    RentContractComponent,
    RentContractDetailComponent,
    RentContractUpdateComponent,
    RentContractDeleteDialogComponent,
    RentContractDeletePopupComponent
  ],
  entryComponents: [
    RentContractComponent,
    RentContractUpdateComponent,
    RentContractDeleteDialogComponent,
    RentContractDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ContractRentContractModule {}
