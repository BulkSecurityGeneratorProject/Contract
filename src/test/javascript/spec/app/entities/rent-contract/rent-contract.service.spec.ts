/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { RentContractService } from 'app/entities/rent-contract/rent-contract.service';
import { IRentContract, RentContract } from 'app/shared/model/rent-contract.model';

describe('Service Tests', () => {
  describe('RentContract Service', () => {
    let injector: TestBed;
    let service: RentContractService;
    let httpMock: HttpTestingController;
    let elemDefault: IRentContract;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(RentContractService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new RentContract(
        0,
        0,
        currentDate,
        currentDate,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            contractSignDate: currentDate.format(DATE_TIME_FORMAT),
            contractStartDate: currentDate.format(DATE_TIME_FORMAT),
            contractEndDate: currentDate.format(DATE_TIME_FORMAT),
            oilChangeTime: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a RentContract', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            contractSignDate: currentDate.format(DATE_TIME_FORMAT),
            contractStartDate: currentDate.format(DATE_TIME_FORMAT),
            contractEndDate: currentDate.format(DATE_TIME_FORMAT),
            oilChangeTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            contractSignDate: currentDate,
            contractStartDate: currentDate,
            contractEndDate: currentDate,
            oilChangeTime: currentDate
          },
          returnedFromService
        );
        service
          .create(new RentContract(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a RentContract', async () => {
        const returnedFromService = Object.assign(
          {
            contractNumber: 1,
            contractSignDate: currentDate.format(DATE_TIME_FORMAT),
            contractStartDate: currentDate.format(DATE_TIME_FORMAT),
            contractEndDate: currentDate.format(DATE_TIME_FORMAT),
            contractSignLocation: 'BBBBBB',
            extendedTo: 'BBBBBB',
            extendedTimes: 1,
            rentDuration: 1,
            rentDayCost: 1,
            rentHourCost: 1,
            odometerKmBefore: 1,
            allowedLateHours: 1,
            lateHourCost: 1,
            allowedKmPerDay: 1,
            allowedKmPerHour: 1,
            carTransferCost: 1,
            receiveLocation: 'BBBBBB',
            returnLocation: 'BBBBBB',
            earlyReturnPolicy: 'BBBBBB',
            contractExtensionMechanism: 'BBBBBB',
            accidentsAndFaultReportingMechanism: 'BBBBBB',
            odometerKmAfter: 1,
            lateHours: 1,
            oilChangeTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            contractSignDate: currentDate,
            contractStartDate: currentDate,
            contractEndDate: currentDate,
            oilChangeTime: currentDate
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

      it('should return a list of RentContract', async () => {
        const returnedFromService = Object.assign(
          {
            contractNumber: 1,
            contractSignDate: currentDate.format(DATE_TIME_FORMAT),
            contractStartDate: currentDate.format(DATE_TIME_FORMAT),
            contractEndDate: currentDate.format(DATE_TIME_FORMAT),
            contractSignLocation: 'BBBBBB',
            extendedTo: 'BBBBBB',
            extendedTimes: 1,
            rentDuration: 1,
            rentDayCost: 1,
            rentHourCost: 1,
            odometerKmBefore: 1,
            allowedLateHours: 1,
            lateHourCost: 1,
            allowedKmPerDay: 1,
            allowedKmPerHour: 1,
            carTransferCost: 1,
            receiveLocation: 'BBBBBB',
            returnLocation: 'BBBBBB',
            earlyReturnPolicy: 'BBBBBB',
            contractExtensionMechanism: 'BBBBBB',
            accidentsAndFaultReportingMechanism: 'BBBBBB',
            odometerKmAfter: 1,
            lateHours: 1,
            oilChangeTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            contractSignDate: currentDate,
            contractStartDate: currentDate,
            contractEndDate: currentDate,
            oilChangeTime: currentDate
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

      it('should delete a RentContract', async () => {
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
