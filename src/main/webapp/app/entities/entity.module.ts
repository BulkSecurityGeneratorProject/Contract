import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'rental-account',
        loadChildren: './rental-account/rental-account.module#ContractRentalAccountModule'
      },
      {
        path: 'branch',
        loadChildren: './branch/branch.module#ContractBranchModule'
      },
      {
        path: 'contract-status',
        loadChildren: './contract-status/contract-status.module#ContractContractStatusModule'
      },
      {
        path: 'contract-type',
        loadChildren: './contract-type/contract-type.module#ContractContractTypeModule'
      },
      {
        path: 'drive-area',
        loadChildren: './drive-area/drive-area.module#ContractDriveAreaModule'
      },
      {
        path: 'id-type',
        loadChildren: './id-type/id-type.module#ContractIdTypeModule'
      },
      {
        path: 'license-type',
        loadChildren: './license-type/license-type.module#ContractLicenseTypeModule'
      },
      {
        path: 'vehicle-type',
        loadChildren: './vehicle-type/vehicle-type.module#ContractVehicleTypeModule'
      },
      {
        path: 'registration-type',
        loadChildren: './registration-type/registration-type.module#ContractRegistrationTypeModule'
      },
      {
        path: 'insurance-type',
        loadChildren: './insurance-type/insurance-type.module#ContractInsuranceTypeModule'
      },
      {
        path: 'oil-type',
        loadChildren: './oil-type/oil-type.module#ContractOilTypeModule'
      },
      {
        path: 'fuel-type',
        loadChildren: './fuel-type/fuel-type.module#ContractFuelTypeModule'
      },
      {
        path: 'payment-method',
        loadChildren: './payment-method/payment-method.module#ContractPaymentMethodModule'
      },
      {
        path: 'nationality',
        loadChildren: './nationality/nationality.module#ContractNationalityModule'
      },
      {
        path: 'rent-contract',
        loadChildren: './rent-contract/rent-contract.module#ContractRentContractModule'
      },
      {
        path: 'vehicle-item-status',
        loadChildren: './vehicle-item-status/vehicle-item-status.module#ContractVehicleItemStatusModule'
      },
      {
        path: 'person-info',
        loadChildren: './person-info/person-info.module#ContractPersonInfoModule'
      },
      {
        path: 'vehicle-details',
        loadChildren: './vehicle-details/vehicle-details.module#ContractVehicleDetailsModule'
      },
      {
        path: 'payment-details',
        loadChildren: './payment-details/payment-details.module#ContractPaymentDetailsModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ContractEntityModule {}
