/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { PaymentDetailsService } from 'app/entities/payment-details/payment-details.service';
import { IPaymentDetails, PaymentDetails } from 'app/shared/model/payment-details.model';

describe('Service Tests', () => {
  describe('PaymentDetails Service', () => {
    let injector: TestBed;
    let service: PaymentDetailsService;
    let httpMock: HttpTestingController;
    let elemDefault: IPaymentDetails;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(PaymentDetailsService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new PaymentDetails(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a PaymentDetails', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new PaymentDetails(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a PaymentDetails', async () => {
        const returnedFromService = Object.assign(
          {
            total: 1,
            totalRentCost: 1,
            extraKmCost: 1,
            driverCost: 1,
            internationalAuthorizationCost: 1,
            vehicleTransferCost: 1,
            sparePartsCost: 1,
            oilChangeCost: 1,
            damageCost: 1,
            fuelCost: 1,
            discountPercentage: 1,
            discount: 1,
            vat: 1,
            paid: 1,
            remaining: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of PaymentDetails', async () => {
        const returnedFromService = Object.assign(
          {
            total: 1,
            totalRentCost: 1,
            extraKmCost: 1,
            driverCost: 1,
            internationalAuthorizationCost: 1,
            vehicleTransferCost: 1,
            sparePartsCost: 1,
            oilChangeCost: 1,
            damageCost: 1,
            fuelCost: 1,
            discountPercentage: 1,
            discount: 1,
            vat: 1,
            paid: 1,
            remaining: 1
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a PaymentDetails', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
