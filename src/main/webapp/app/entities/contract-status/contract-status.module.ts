import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ContractSharedModule } from 'app/shared';
import {
  ContractStatusComponent,
  ContractStatusDetailComponent,
  ContractStatusUpdateComponent,
  ContractStatusDeletePopupComponent,
  ContractStatusDeleteDialogComponent,
  contractStatusRoute,
  contractStatusPopupRoute
} from './';

const ENTITY_STATES = [...contractStatusRoute, ...contractStatusPopupRoute];

@NgModule({
  imports: [ContractSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ContractStatusComponent,
    ContractStatusDetailComponent,
    ContractStatusUpdateComponent,
    ContractStatusDeleteDialogComponent,
    ContractStatusDeletePopupComponent
  ],
  entryComponents: [
    ContractStatusComponent,
    ContractStatusUpdateComponent,
    ContractStatusDeleteDialogComponent,
    ContractStatusDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ContractContractStatusModule {}
