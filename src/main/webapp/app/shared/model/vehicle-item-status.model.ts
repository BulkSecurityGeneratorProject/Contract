export interface IVehicleItemStatus {
  id?: number;
  ac?: number;
  radioStereo?: number;
  screen?: number;
  speedometer?: number;
  carSeats?: number;
  tires?: number;
  spareTire?: number;
  spareTireTools?: number;
  firtsAidKit?: number;
  key?: number;
  fireExtinguisher?: number;
  safetyTriangle?: number;
}

export class VehicleItemStatus implements IVehicleItemStatus {
  constructor(
    public id?: number,
    public ac?: number,
    public radioStereo?: number,
    public screen?: number,
    public speedometer?: number,
    public carSeats?: number,
    public tires?: number,
    public spareTire?: number,
    public spareTireTools?: number,
    public firtsAidKit?: number,
    public key?: number,
    public fireExtinguisher?: number,
    public safetyTriangle?: number
  ) {}
}
