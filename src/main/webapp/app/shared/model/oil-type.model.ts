import { IVehicleDetails } from 'app/shared/model/vehicle-details.model';

export interface IOilType {
  id?: number;
  arName?: string;
  enName?: string;
  vehicleDetails?: IVehicleDetails;
}

export class OilType implements IOilType {
  constructor(public id?: number, public arName?: string, public enName?: string, public vehicleDetails?: IVehicleDetails) {}
}
