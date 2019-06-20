import { IPaymentDetails } from 'app/shared/model/payment-details.model';

export interface IPaymentMethod {
  id?: number;
  arName?: string;
  enName?: string;
  paymentDetails?: IPaymentDetails;
}

export class PaymentMethod implements IPaymentMethod {
  constructor(public id?: number, public arName?: string, public enName?: string, public paymentDetails?: IPaymentDetails) {}
}
