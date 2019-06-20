/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { VehicleDetailsService } from 'app/entities/vehicle-details/vehicle-details.service';
import { IVehicleDetails, VehicleDetails } from 'app/shared/model/vehicle-details.model';

describe('Service Tests', () => {
  describe('VehicleDetails Service', () => {
    let injector: TestBed;
    let service: VehicleDetailsService;
    let httpMock: HttpTestingController;
    let elemDefault: IVehicleDetails;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(VehicleDetailsService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new VehicleDetails(
        0,
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        currentDate,
        0,
        0,
        0
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            operationCardExpiryDate: currentDate.format(DATE_TIME_FORMAT),
            insuranceExpiryDate: currentDate.format(DATE_TIME_FORMAT),
            oilChangeDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a VehicleDetails', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            operationCardExpiryDate: currentDate.format(DATE_TIME_FORMAT),
            insuranceExpiryDate: currentDate.format(DATE_TIME_FORMAT),
            oilChangeDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            operationCardExpiryDate: currentDate,
            insuranceExpiryDate: currentDate,
            oilChangeDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new VehicleDetails(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a VehicleDetails', async () => {
        const returnedFromService = Object.assign(
          {
            plateNumber: 'BBBBBB',
            manufactureYear: 1,
            color: 'BBBBBB',
            operationCardNumber: 'BBBBBB',
            operationCardExpiryDate: currentDate.format(DATE_TIME_FORMAT),
            insuranceNumber: 'BBBBBB',
            insuranceExpiryDate: currentDate.format(DATE_TIME_FORMAT),
            additionalInsurance: 'BBBBBB',
            oilChangeDate: currentDate.format(DATE_TIME_FORMAT),
            oilChangeKmDistance: 1,
            availableFuel: 1,
            enduranceAmount: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            operationCardExpiryDate: currentDate,
            insuranceExpiryDate: currentDate,
            oilChangeDate: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of VehicleDetails', async () => {
        const returnedFromService = Object.assign(
          {
            plateNumber: 'BBBBBB',
            manufactureYear: 1,
            color: 'BBBBBB',
            operationCardNumber: 'BBBBBB',
            operationCardExpiryDate: currentDate.format(DATE_TIME_FORMAT),
            insuranceNumber: 'BBBBBB',
            insuranceExpiryDate: currentDate.format(DATE_TIME_FORMAT),
            additionalInsurance: 'BBBBBB',
            oilChangeDate: currentDate.format(DATE_TIME_FORMAT),
            oilChangeKmDistance: 1,
            availableFuel: 1,
            enduranceAmount: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            operationCardExpiryDate: currentDate,
            insuranceExpiryDate: currentDate,
            oilChangeDate: currentDate
          },
          returnedFromService
        );
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

      it('should delete a VehicleDetails', async () => {
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
