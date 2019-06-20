import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ContractSharedModule } from 'app/shared';
import {
  PersonInfoComponent,
  PersonInfoDetailComponent,
  PersonInfoUpdateComponent,
  PersonInfoDeletePopupComponent,
  PersonInfoDeleteDialogComponent,
  personInfoRoute,
  personInfoPopupRoute
} from './';

const ENTITY_STATES = [...personInfoRoute, ...personInfoPopupRoute];

@NgModule({
  imports: [ContractSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PersonInfoComponent,
    PersonInfoDetailComponent,
    PersonInfoUpdateComponent,
    PersonInfoDeleteDialogComponent,
    PersonInfoDeletePopupComponent
  ],
  entryComponents: [PersonInfoComponent, PersonInfoUpdateComponent, PersonInfoDeleteDialogComponent, PersonInfoDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ContractPersonInfoModule {}
