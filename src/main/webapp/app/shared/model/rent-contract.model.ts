import { Moment } from 'moment';
import { IVehicleItemStatus } from 'app/shared/model/vehicle-item-status.model';
import { IPersonInfo } from 'app/shared/model/person-info.model';
import { IVehicleDetails } from 'app/shared/model/vehicle-details.model';
import { IPaymentDetails } from 'app/shared/model/payment-details.model';
import { IContractStatus } from 'app/shared/model/contract-status.model';
import { IContractType } from 'app/shared/model/contract-type.model';
import { IDriveArea } from 'app/shared/model/drive-area.model';
import { IRentalAccount } from 'app/shared/model/rental-account.model';
import { IBranch } from 'app/shared/model/branch.model';

export interface IRentContract {
  id?: number;
  contractNumber?: number;
  contractSignDate?: Moment;
  contractStartDate?: Moment;
  contractEndDate?: Moment;
  contractSignLocation?: string;
  extendedTo?: string;
  extendedTimes?: number;
  rentDuration?: number;
  rentDayCost?: number;
  rentHourCost?: number;
  odometerKmBefore?: number;
  allowedLateHours?: number;
  lateHourCost?: number;
  allowedKmPerDay?: number;
  allowedKmPerHour?: number;
  carTransferCost?: number;
  receiveLocation?: string;
  returnLocation?: string;
  earlyReturnPolicy?: string;
  contractExtensionMechanism?: string;
  accidentsAndFaultReportingMechanism?: string;
  odometerKmAfter?: number;
  lateHours?: number;
  oilChangeTime?: Moment;
  rentStatus?: IVehicleItemStatus;
  returnStatus?: IVehicleItemStatus;
  renter?: IPersonInfo;
  extraDriver?: IPersonInfo;
  rentedDriver?: IPersonInfo;
  vehicleDetails?: IVehicleDetails;
  paymentDetails?: IPaymentDetails;
  contractStatuses?: IContractStatus[];
  contractTypes?: IContractType[];
  driveAreas?: IDriveArea[];
  account?: IRentalAccount;
  branch?: IBranch;
}

export class RentContract implements IRentContract {
  constructor(
    public id?: number,
    public contractNumber?: number,
    public contractSignDate?: Moment,
    public contractStartDate?: Moment,
    public contractEndDate?: Moment,
    public contractSignLocation?: string,
    public extendedTo?: string,
    public extendedTimes?: number,
    public rentDuration?: number,
    public rentDayCost?: number,
    public rentHourCost?: number,
    public odometerKmBefore?: number,
    public allowedLateHours?: number,
    public lateHourCost?: number,
    public allowedKmPerDay?: number,
    public allowedKmPerHour?: number,
    public carTransferCost?: number,
    public receiveLocation?: string,
    public returnLocation?: string,
    public earlyReturnPolicy?: string,
    public contractExtensionMechanism?: string,
    public accidentsAndFaultReportingMechanism?: string,
    public odometerKmAfter?: number,
    public lateHours?: number,
    public oilChangeTime?: Moment,
    public rentStatus?: IVehicleItemStatus,
    public returnStatus?: IVehicleItemStatus,
    public renter?: IPersonInfo,
    public extraDriver?: IPersonInfo,
    public rentedDriver?: IPersonInfo,
    public vehicleDetails?: IVehicleDetails,
    public paymentDetails?: IPaymentDetails,
    public contractStatuses?: IContractStatus[],
    public contractTypes?: IContractType[],
    public driveAreas?: IDriveArea[],
    public account?: IRentalAccount,
    public branch?: IBranch
  ) {}
}
