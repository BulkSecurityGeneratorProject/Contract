import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ContractSharedModule } from 'app/shared';
import {
  OilTypeComponent,
  OilTypeDetailComponent,
  OilTypeUpdateComponent,
  OilTypeDeletePopupComponent,
  OilTypeDeleteDialogComponent,
  oilTypeRoute,
  oilTypePopupRoute
} from './';

const ENTITY_STATES = [...oilTypeRoute, ...oilTypePopupRoute];

@NgModule({
  imports: [ContractSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    OilTypeComponent,
    OilTypeDetailComponent,
    OilTypeUpdateComponent,
    OilTypeDeleteDialogComponent,
    OilTypeDeletePopupComponent
  ],
  entryComponents: [OilTypeComponent, OilTypeUpdateComponent, OilTypeDeleteDialogComponent, OilTypeDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ContractOilTypeModule {}
