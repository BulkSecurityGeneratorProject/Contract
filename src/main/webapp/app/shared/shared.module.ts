import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ContractSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [ContractSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [ContractSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ContractSharedModule {
  static forRoot() {
    return {
      ngModule: ContractSharedModule
    };
  }
}
