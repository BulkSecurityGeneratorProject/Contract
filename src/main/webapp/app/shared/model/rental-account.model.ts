export interface IRentalAccount {
  id?: number;
  arName?: string;
  enName?: string;
}

export class RentalAccount implements IRentalAccount {
  constructor(public id?: number, public arName?: string, public enName?: string) {}
}
