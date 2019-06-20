import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ContractSharedModule } from 'app/shared';
import {
  ContractTypeComponent,
  ContractTypeDetailComponent,
  ContractTypeUpdateComponent,
  ContractTypeDeletePopupComponent,
  ContractTypeDeleteDialogComponent,
  contractTypeRoute,
  contractTypePopupRoute
} from './';

const ENTITY_STATES = [...contractTypeRoute, ...contractTypePopupRoute];

@NgModule({
  imports: [ContractSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ContractTypeComponent,
    ContractTypeDetailComponent,
    ContractTypeUpdateComponent,
    ContractTypeDeleteDialogComponent,
    ContractTypeDeletePopupComponent
  ],
  entryComponents: [
    ContractTypeComponent,
    ContractTypeUpdateComponent,
    ContractTypeDeleteDialogComponent,
    ContractTypeDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ContractContractTypeModule {}
