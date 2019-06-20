import { IVehicleDetails } from 'app/shared/model/vehicle-details.model';

export interface IFuelType {
  id?: number;
  arName?: string;
  enName?: string;
  vehicleDetails?: IVehicleDetails;
}

export class FuelType implements IFuelType {
  constructor(public id?: number, public arName?: string, public enName?: string, public vehicleDetails?: IVehicleDetails) {}
}
