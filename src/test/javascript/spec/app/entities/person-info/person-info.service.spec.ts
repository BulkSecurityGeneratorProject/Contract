/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { PersonInfoService } from 'app/entities/person-info/person-info.service';
import { IPersonInfo, PersonInfo } from 'app/shared/model/person-info.model';

describe('Service Tests', () => {
  describe('PersonInfo Service', () => {
    let injector: TestBed;
    let service: PersonInfoService;
    let httpMock: HttpTestingController;
    let elemDefault: IPersonInfo;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(PersonInfoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new PersonInfo(
        0,
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        0,
        currentDate,
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            birthDate: currentDate.format(DATE_TIME_FORMAT),
            idExpiryDate: currentDate.format(DATE_TIME_FORMAT),
            licenseExpiryDate: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a PersonInfo', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            birthDate: currentDate.format(DATE_TIME_FORMAT),
            idExpiryDate: currentDate.format(DATE_TIME_FORMAT),
            licenseExpiryDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            birthDate: currentDate,
            idExpiryDate: currentDate,
            licenseExpiryDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new PersonInfo(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a PersonInfo', async () => {
        const returnedFromService = Object.assign(
          {
            arFullName: 'BBBBBB',
            enFullName: 'BBBBBB',
            birthDate: currentDate.format(DATE_TIME_FORMAT),
            hijriBirthDate: 1,
            idExpiryDate: currentDate.format(DATE_TIME_FORMAT),
            hijriIdExpiryDate: 1,
            idCopyNumber: 1,
            issuePlace: 'BBBBBB',
            mobile: 'BBBBBB',
            licenseExpiryDate: currentDate.format(DATE_TIME_FORMAT),
            address: 'BBBBBB',
            nationalAddress: 'BBBBBB',
            workAddress: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            birthDate: currentDate,
            idExpiryDate: currentDate,
            licenseExpiryDate: currentDate
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

      it('should return a list of PersonInfo', async () => {
        const returnedFromService = Object.assign(
          {
            arFullName: 'BBBBBB',
            enFullName: 'BBBBBB',
            birthDate: currentDate.format(DATE_TIME_FORMAT),
            hijriBirthDate: 1,
            idExpiryDate: currentDate.format(DATE_TIME_FORMAT),
            hijriIdExpiryDate: 1,
            idCopyNumber: 1,
            issuePlace: 'BBBBBB',
            mobile: 'BBBBBB',
            licenseExpiryDate: currentDate.format(DATE_TIME_FORMAT),
            address: 'BBBBBB',
            nationalAddress: 'BBBBBB',
            workAddress: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            birthDate: currentDate,
            idExpiryDate: currentDate,
            licenseExpiryDate: currentDate
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

      it('should delete a PersonInfo', async () => {
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
