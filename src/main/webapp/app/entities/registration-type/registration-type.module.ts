import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ContractSharedModule } from 'app/shared';
import {
  RegistrationTypeComponent,
  RegistrationTypeDetailComponent,
  RegistrationTypeUpdateComponent,
  RegistrationTypeDeletePopupComponent,
  RegistrationTypeDeleteDialogComponent,
  registrationTypeRoute,
  registrationTypePopupRoute
} from './';

const ENTITY_STATES = [...registrationTypeRoute, ...registrationTypePopupRoute];

@NgModule({
  imports: [ContractSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    RegistrationTypeComponent,
    RegistrationTypeDetailComponent,
    RegistrationTypeUpdateComponent,
    RegistrationTypeDeleteDialogComponent,
    RegistrationTypeDeletePopupComponent
  ],
  entryComponents: [
    RegistrationTypeComponent,
    RegistrationTypeUpdateComponent,
    RegistrationTypeDeleteDialogComponent,
    RegistrationTypeDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ContractRegistrationTypeModule {}
