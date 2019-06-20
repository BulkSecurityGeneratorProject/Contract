import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ContractSharedModule } from 'app/shared';
import {
  IdTypeComponent,
  IdTypeDetailComponent,
  IdTypeUpdateComponent,
  IdTypeDeletePopupComponent,
  IdTypeDeleteDialogComponent,
  idTypeRoute,
  idTypePopupRoute
} from './';

const ENTITY_STATES = [...idTypeRoute, ...idTypePopupRoute];

@NgModule({
  imports: [ContractSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [IdTypeComponent, IdTypeDetailComponent, IdTypeUpdateComponent, IdTypeDeleteDialogComponent, IdTypeDeletePopupComponent],
  entryComponents: [IdTypeComponent, IdTypeUpdateComponent, IdTypeDeleteDialogComponent, IdTypeDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ContractIdTypeModule {}
