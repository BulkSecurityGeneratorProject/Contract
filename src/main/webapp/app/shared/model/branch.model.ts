export interface IBranch {
  id?: number;
  arName?: string;
  enName?: string;
}

export class Branch implements IBranch {
  constructor(public id?: number, public arName?: string, public enName?: string) {}
}
