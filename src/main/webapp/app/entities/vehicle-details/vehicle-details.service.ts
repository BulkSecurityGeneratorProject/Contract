import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IVehicleDetails } from 'app/shared/model/vehicle-details.model';

type EntityResponseType = HttpResponse<IVehicleDetails>;
type EntityArrayResponseType = HttpResponse<IVehicleDetails[]>;

@Injectable({ providedIn: 'root' })
export class VehicleDetailsService {
  public resourceUrl = SERVER_API_URL + 'api/vehicle-details';

  constructor(protected http: HttpClient) {}

  create(vehicleDetails: IVehicleDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vehicleDetails);
    return this.http
      .post<IVehicleDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(vehicleDetails: IVehicleDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vehicleDetails);
    return this.http
      .put<IVehicleDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IVehicleDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVehicleDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(vehicleDetails: IVehicleDetails): IVehicleDetails {
    const copy: IVehicleDetails = Object.assign({}, vehicleDetails, {
      operationCardExpiryDate:
        vehicleDetails.operationCardExpiryDate != null && vehicleDetails.operationCardExpiryDate.isValid()
          ? vehicleDetails.operationCardExpiryDate.toJSON()
          : null,
      insuranceExpiryDate:
        vehicleDetails.insuranceExpiryDate != null && vehicleDetails.insuranceExpiryDate.isValid()
          ? vehicleDetails.insuranceExpiryDate.toJSON()
          : null,
      oilChangeDate:
        vehicleDetails.oilChangeDate != null && vehicleDetails.oilChangeDate.isValid() ? vehicleDetails.oilChangeDate.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.operationCardExpiryDate = res.body.operationCardExpiryDate != null ? moment(res.body.operationCardExpiryDate) : null;
      res.body.insuranceExpiryDate = res.body.insuranceExpiryDate != null ? moment(res.body.insuranceExpiryDate) : null;
      res.body.oilChangeDate = res.body.oilChangeDate != null ? moment(res.body.oilChangeDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((vehicleDetails: IVehicleDetails) => {
        vehicleDetails.operationCardExpiryDate =
          vehicleDetails.operationCardExpiryDate != null ? moment(vehicleDetails.operationCardExpiryDate) : null;
        vehicleDetails.insuranceExpiryDate = vehicleDetails.insuranceExpiryDate != null ? moment(vehicleDetails.insuranceExpiryDate) : null;
        vehicleDetails.oilChangeDate = vehicleDetails.oilChangeDate != null ? moment(vehicleDetails.oilChangeDate) : null;
      });
    }
    return res;
  }
}
