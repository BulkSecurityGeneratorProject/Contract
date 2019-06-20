import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ContractSharedModule } from 'app/shared';
import {
  DriveAreaComponent,
  DriveAreaDetailComponent,
  DriveAreaUpdateComponent,
  DriveAreaDeletePopupComponent,
  DriveAreaDeleteDialogComponent,
  driveAreaRoute,
  driveAreaPopupRoute
} from './';

const ENTITY_STATES = [...driveAreaRoute, ...driveAreaPopupRoute];

@NgModule({
  imports: [ContractSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DriveAreaComponent,
    DriveAreaDetailComponent,
    DriveAreaUpdateComponent,
    DriveAreaDeleteDialogComponent,
    DriveAreaDeletePopupComponent
  ],
  entryComponents: [DriveAreaComponent, DriveAreaUpdateComponent, DriveAreaDeleteDialogComponent, DriveAreaDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ContractDriveAreaModule {}
