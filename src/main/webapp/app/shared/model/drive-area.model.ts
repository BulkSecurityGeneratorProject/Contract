import { IRentContract } from 'app/shared/model/rent-contract.model';

export interface IDriveArea {
  id?: number;
  arName?: string;
  enName?: string;
  rentContract?: IRentContract;
}

export class DriveArea implements IDriveArea {
  constructor(public id?: number, public arName?: string, public enName?: string, public rentContract?: IRentContract) {}
}
