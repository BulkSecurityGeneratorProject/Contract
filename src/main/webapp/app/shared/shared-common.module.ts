import { NgModule } from '@angular/core';

import { ContractSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [ContractSharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [ContractSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class ContractSharedCommonModule {}
