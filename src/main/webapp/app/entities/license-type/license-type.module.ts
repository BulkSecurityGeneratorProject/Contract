import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ContractSharedModule } from 'app/shared';
import {
  LicenseTypeComponent,
  LicenseTypeDetailComponent,
  LicenseTypeUpdateComponent,
  LicenseTypeDeletePopupComponent,
  LicenseTypeDeleteDialogComponent,
  licenseTypeRoute,
  licenseTypePopupRoute
} from './';

const ENTITY_STATES = [...licenseTypeRoute, ...licenseTypePopupRoute];

@NgModule({
  imports: [ContractSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    LicenseTypeComponent,
    LicenseTypeDetailComponent,
    LicenseTypeUpdateComponent,
    LicenseTypeDeleteDialogComponent,
    LicenseTypeDeletePopupComponent
  ],
  entryComponents: [LicenseTypeComponent, LicenseTypeUpdateComponent, LicenseTypeDeleteDialogComponent, LicenseTypeDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ContractLicenseTypeModule {}
