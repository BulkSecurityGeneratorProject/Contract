import { IRentContract } from 'app/shared/model/rent-contract.model';

export interface IContractType {
  id?: number;
  arName?: string;
  enName?: string;
  rentContract?: IRentContract;
}

export class ContractType implements IContractType {
  constructor(public id?: number, public arName?: string, public enName?: string, public rentContract?: IRentContract) {}
}
