import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRentContract } from 'app/shared/model/rent-contract.model';

type EntityResponseType = HttpResponse<IRentContract>;
type EntityArrayResponseType = HttpResponse<IRentContract[]>;

@Injectable({ providedIn: 'root' })
export class RentContractService {
  public resourceUrl = SERVER_API_URL + 'api/rent-contracts';

  constructor(protected http: HttpClient) {}

  create(rentContract: IRentContract): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(rentContract);
    return this.http
      .post<IRentContract>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(rentContract: IRentContract): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(rentContract);
    return this.http
      .put<IRentContract>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRentContract>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRentContract[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(rentContract: IRentContract): IRentContract {
    const copy: IRentContract = Object.assign({}, rentContract, {
      contractSignDate:
        rentContract.contractSignDate != null && rentContract.contractSignDate.isValid() ? rentContract.contractSignDate.toJSON() : null,
      contractStartDate:
        rentContract.contractStartDate != null && rentContract.contractStartDate.isValid() ? rentContract.contractStartDate.toJSON() : null,
      contractEndDate:
        rentContract.contractEndDate != null && rentContract.contractEndDate.isValid() ? rentContract.contractEndDate.toJSON() : null,
      oilChangeTime: rentContract.oilChangeTime != null && rentContract.oilChangeTime.isValid() ? rentContract.oilChangeTime.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.contractSignDate = res.body.contractSignDate != null ? moment(res.body.contractSignDate) : null;
      res.body.contractStartDate = res.body.contractStartDate != null ? moment(res.body.contractStartDate) : null;
      res.body.contractEndDate = res.body.contractEndDate != null ? moment(res.body.contractEndDate) : null;
      res.body.oilChangeTime = res.body.oilChangeTime != null ? moment(res.body.oilChangeTime) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((rentContract: IRentContract) => {
        rentContract.contractSignDate = rentContract.contractSignDate != null ? moment(rentContract.contractSignDate) : null;
        rentContract.contractStartDate = rentContract.contractStartDate != null ? moment(rentContract.contractStartDate) : null;
        rentContract.contractEndDate = rentContract.contractEndDate != null ? moment(rentContract.contractEndDate) : null;
        rentContract.oilChangeTime = rentContract.oilChangeTime != null ? moment(rentContract.oilChangeTime) : null;
      });
    }
    return res;
  }
}
