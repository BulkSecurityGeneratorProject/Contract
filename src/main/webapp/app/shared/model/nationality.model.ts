import { IPersonInfo } from 'app/shared/model/person-info.model';

export interface INationality {
  id?: number;
  arName?: string;
  enName?: string;
  personInfo?: IPersonInfo;
}

export class Nationality implements INationality {
  constructor(public id?: number, public arName?: string, public enName?: string, public personInfo?: IPersonInfo) {}
}
