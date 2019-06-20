import { IVehicleDetails } from 'app/shared/model/vehicle-details.model';

export interface IInsuranceType {
  id?: number;
  arName?: string;
  enName?: string;
  vehicleDetails?: IVehicleDetails;
}

export class InsuranceType implements IInsuranceType {
  constructor(public id?: number, public arName?: string, public enName?: string, public vehicleDetails?: IVehicleDetails) {}
}
