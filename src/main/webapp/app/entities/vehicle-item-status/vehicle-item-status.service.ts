import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IVehicleItemStatus } from 'app/shared/model/vehicle-item-status.model';

type EntityResponseType = HttpResponse<IVehicleItemStatus>;
type EntityArrayResponseType = HttpResponse<IVehicleItemStatus[]>;

@Injectable({ providedIn: 'root' })
export class VehicleItemStatusService {
  public resourceUrl = SERVER_API_URL + 'api/vehicle-item-statuses';

  constructor(protected http: HttpClient) {}

  create(vehicleItemStatus: IVehicleItemStatus): Observable<EntityResponseType> {
    return this.http.post<IVehicleItemStatus>(this.resourceUrl, vehicleItemStatus, { observe: 'response' });
  }

  update(vehicleItemStatus: IVehicleItemStatus): Observable<EntityResponseType> {
    return this.http.put<IVehicleItemStatus>(this.resourceUrl, vehicleItemStatus, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVehicleItemStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVehicleItemStatus[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
