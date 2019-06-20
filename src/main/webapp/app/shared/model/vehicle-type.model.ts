import { IVehicleDetails } from 'app/shared/model/vehicle-details.model';

export interface IVehicleType {
  id?: number;
  arName?: string;
  enName?: string;
  vehicleDetails?: IVehicleDetails;
}

export class VehicleType implements IVehicleType {
  constructor(public id?: number, public arName?: string, public enName?: string, public vehicleDetails?: IVehicleDetails) {}
}
