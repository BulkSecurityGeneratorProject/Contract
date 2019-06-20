import { Moment } from 'moment';
import { IIdType } from 'app/shared/model/id-type.model';
import { INationality } from 'app/shared/model/nationality.model';
import { ILicenseType } from 'app/shared/model/license-type.model';

export interface IPersonInfo {
  id?: number;
  arFullName?: string;
  enFullName?: string;
  birthDate?: Moment;
  hijriBirthDate?: number;
  idExpiryDate?: Moment;
  hijriIdExpiryDate?: number;
  idCopyNumber?: number;
  issuePlace?: string;
  mobile?: string;
  licenseExpiryDate?: Moment;
  address?: string;
  nationalAddress?: string;
  workAddress?: string;
  idTypes?: IIdType[];
  nationalities?: INationality[];
  licenseTypes?: ILicenseType[];
}

export class PersonInfo implements IPersonInfo {
  constructor(
    public id?: number,
    public arFullName?: string,
    public enFullName?: string,
    public birthDate?: Moment,
    public hijriBirthDate?: number,
    public idExpiryDate?: Moment,
    public hijriIdExpiryDate?: number,
    public idCopyNumber?: number,
    public issuePlace?: string,
    public mobile?: string,
    public licenseExpiryDate?: Moment,
    public address?: string,
    public nationalAddress?: string,
    public workAddress?: string,
    public idTypes?: IIdType[],
    public nationalities?: INationality[],
    public licenseTypes?: ILicenseType[]
  ) {}
}
