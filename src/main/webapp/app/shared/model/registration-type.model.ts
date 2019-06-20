import { IVehicleDetails } from 'app/shared/model/vehicle-details.model';

export interface IRegistrationType {
  id?: number;
  arName?: string;
  enName?: string;
  vehicleDetails?: IVehicleDetails;
}

export class RegistrationType implements IRegistrationType {
  constructor(public id?: number, public arName?: string, public enName?: string, public vehicleDetails?: IVehicleDetails) {}
}
