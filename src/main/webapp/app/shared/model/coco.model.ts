export interface ICOCO {
  id?: number;
  coco?: string;
}

export class COCO implements ICOCO {
  constructor(public id?: number, public coco?: string) {}
}
