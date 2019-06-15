import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ContractSharedModule } from 'app/shared';
import {
  COCOComponent,
  COCODetailComponent,
  COCOUpdateComponent,
  COCODeletePopupComponent,
  COCODeleteDialogComponent,
  cOCORoute,
  cOCOPopupRoute
} from './';

const ENTITY_STATES = [...cOCORoute, ...cOCOPopupRoute];

@NgModule({
  imports: [ContractSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [COCOComponent, COCODetailComponent, COCOUpdateComponent, COCODeleteDialogComponent, COCODeletePopupComponent],
  entryComponents: [COCOComponent, COCOUpdateComponent, COCODeleteDialogComponent, COCODeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ContractCOCOModule {}
