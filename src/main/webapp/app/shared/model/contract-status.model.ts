import { IRentContract } from 'app/shared/model/rent-contract.model';

export interface IContractStatus {
  id?: number;
  arName?: string;
  enName?: string;
  rentContract?: IRentContract;
}

export class ContractStatus implements IContractStatus {
  constructor(public id?: number, public arName?: string, public enName?: string, public rentContract?: IRentContract) {}
}
