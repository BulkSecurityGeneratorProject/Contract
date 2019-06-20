import { IPersonInfo } from 'app/shared/model/person-info.model';

export interface IIdType {
  id?: number;
  arName?: string;
  enName?: string;
  personInfo?: IPersonInfo;
}

export class IdType implements IIdType {
  constructor(public id?: number, public arName?: string, public enName?: string, public personInfo?: IPersonInfo) {}
}
