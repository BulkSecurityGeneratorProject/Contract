import { IPaymentMethod } from 'app/shared/model/payment-method.model';

export interface IPaymentDetails {
  id?: number;
  total?: number;
  totalRentCost?: number;
  extraKmCost?: number;
  driverCost?: number;
  internationalAuthorizationCost?: number;
  vehicleTransferCost?: number;
  sparePartsCost?: number;
  oilChangeCost?: number;
  damageCost?: number;
  fuelCost?: number;
  discountPercentage?: number;
  discount?: number;
  vat?: number;
  paid?: number;
  remaining?: number;
  paymentMethods?: IPaymentMethod[];
}

export class PaymentDetails implements IPaymentDetails {
  constructor(
    public id?: number,
    public total?: number,
    public totalRentCost?: number,
    public extraKmCost?: number,
    public driverCost?: number,
    public internationalAuthorizationCost?: number,
    public vehicleTransferCost?: number,
    public sparePartsCost?: number,
    public oilChangeCost?: number,
    public damageCost?: number,
    public fuelCost?: number,
    public discountPercentage?: number,
    public discount?: number,
    public vat?: number,
    public paid?: number,
    public remaining?: number,
    public paymentMethods?: IPaymentMethod[]
  ) {}
}
