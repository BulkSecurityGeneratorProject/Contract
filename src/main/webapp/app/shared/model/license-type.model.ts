import { IPersonInfo } from 'app/shared/model/person-info.model';

export interface ILicenseType {
  id?: number;
  arName?: string;
  enName?: string;
  personInfo?: IPersonInfo;
}

export class LicenseType implements ILicenseType {
  constructor(public id?: number, public arName?: string, public enName?: string, public personInfo?: IPersonInfo) {}
}
