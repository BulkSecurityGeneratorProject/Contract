import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFuelType } from 'app/shared/model/fuel-type.model';

type EntityResponseType = HttpResponse<IFuelType>;
type EntityArrayResponseType = HttpResponse<IFuelType[]>;

@Injectable({ providedIn: 'root' })
export class FuelTypeService {
  public resourceUrl = SERVER_API_URL + 'api/fuel-types';

  constructor(protected http: HttpClient) {}

  create(fuelType: IFuelType): Observable<EntityResponseType> {
    return this.http.post<IFuelType>(this.resourceUrl, fuelType, { observe: 'response' });
  }

  update(fuelType: IFuelType): Observable<EntityResponseType> {
    return this.http.put<IFuelType>(this.resourceUrl, fuelType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFuelType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFuelType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
