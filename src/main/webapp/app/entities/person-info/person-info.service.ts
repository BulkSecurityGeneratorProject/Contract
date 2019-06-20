import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPersonInfo } from 'app/shared/model/person-info.model';

type EntityResponseType = HttpResponse<IPersonInfo>;
type EntityArrayResponseType = HttpResponse<IPersonInfo[]>;

@Injectable({ providedIn: 'root' })
export class PersonInfoService {
  public resourceUrl = SERVER_API_URL + 'api/person-infos';

  constructor(protected http: HttpClient) {}

  create(personInfo: IPersonInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(personInfo);
    return this.http
      .post<IPersonInfo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(personInfo: IPersonInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(personInfo);
    return this.http
      .put<IPersonInfo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPersonInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPersonInfo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(personInfo: IPersonInfo): IPersonInfo {
    const copy: IPersonInfo = Object.assign({}, personInfo, {
      birthDate: personInfo.birthDate != null && personInfo.birthDate.isValid() ? personInfo.birthDate.toJSON() : null,
      idExpiryDate: personInfo.idExpiryDate != null && personInfo.idExpiryDate.isValid() ? personInfo.idExpiryDate.toJSON() : null,
      licenseExpiryDate:
        personInfo.licenseExpiryDate != null && personInfo.licenseExpiryDate.isValid() ? personInfo.licenseExpiryDate.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.birthDate = res.body.birthDate != null ? moment(res.body.birthDate) : null;
      res.body.idExpiryDate = res.body.idExpiryDate != null ? moment(res.body.idExpiryDate) : null;
      res.body.licenseExpiryDate = res.body.licenseExpiryDate != null ? moment(res.body.licenseExpiryDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((personInfo: IPersonInfo) => {
        personInfo.birthDate = personInfo.birthDate != null ? moment(personInfo.birthDate) : null;
        personInfo.idExpiryDate = personInfo.idExpiryDate != null ? moment(personInfo.idExpiryDate) : null;
        personInfo.licenseExpiryDate = personInfo.licenseExpiryDate != null ? moment(personInfo.licenseExpiryDate) : null;
      });
    }
    return res;
  }
}
