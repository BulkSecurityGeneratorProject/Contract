import { Moment } from 'moment';
import { IVehicleType } from 'app/shared/model/vehicle-type.model';
import { IRegistrationType } from 'app/shared/model/registration-type.model';
import { IInsuranceType } from 'app/shared/model/insurance-type.model';
import { IOilType } from 'app/shared/model/oil-type.model';
import { IFuelType } from 'app/shared/model/fuel-type.model';

export interface IVehicleDetails {
  id?: number;
  plateNumber?: string;
  manufactureYear?: number;
  color?: string;
  operationCardNumber?: string;
  operationCardExpiryDate?: Moment;
  insuranceNumber?: string;
  insuranceExpiryDate?: Moment;
  additionalInsurance?: string;
  oilChangeDate?: Moment;
  oilChangeKmDistance?: number;
  availableFuel?: number;
  enduranceAmount?: number;
  types?: IVehicleType[];
  registrationTypes?: IRegistrationType[];
  insuranceTypes?: IInsuranceType[];
  oilTypes?: IOilType[];
  fuelTypes?: IFuelType[];
}

export class VehicleDetails implements IVehicleDetails {
  constructor(
    public id?: number,
    public plateNumber?: string,
    public manufactureYear?: number,
    public color?: string,
    public operationCardNumber?: string,
    public operationCardExpiryDate?: Moment,
    public insuranceNumber?: string,
    public insuranceExpiryDate?: Moment,
    public additionalInsurance?: string,
    public oilChangeDate?: Moment,
    public oilChangeKmDistance?: number,
    public availableFuel?: number,
    public enduranceAmount?: number,
    public types?: IVehicleType[],
    public registrationTypes?: IRegistrationType[],
    public insuranceTypes?: IInsuranceType[],
    public oilTypes?: IOilType[],
    public fuelTypes?: IFuelType[]
  ) {}
}
