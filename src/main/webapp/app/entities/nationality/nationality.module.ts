import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ContractSharedModule } from 'app/shared';
import {
  NationalityComponent,
  NationalityDetailComponent,
  NationalityUpdateComponent,
  NationalityDeletePopupComponent,
  NationalityDeleteDialogComponent,
  nationalityRoute,
  nationalityPopupRoute
} from './';

const ENTITY_STATES = [...nationalityRoute, ...nationalityPopupRoute];

@NgModule({
  imports: [ContractSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    NationalityComponent,
    NationalityDetailComponent,
    NationalityUpdateComponent,
    NationalityDeleteDialogComponent,
    NationalityDeletePopupComponent
  ],
  entryComponents: [NationalityComponent, NationalityUpdateComponent, NationalityDeleteDialogComponent, NationalityDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ContractNationalityModule {}
