import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOilType } from 'app/shared/model/oil-type.model';

type EntityResponseType = HttpResponse<IOilType>;
type EntityArrayResponseType = HttpResponse<IOilType[]>;

@Injectable({ providedIn: 'root' })
export class OilTypeService {
  public resourceUrl = SERVER_API_URL + 'api/oil-types';

  constructor(protected http: HttpClient) {}

  create(oilType: IOilType): Observable<EntityResponseType> {
    return this.http.post<IOilType>(this.resourceUrl, oilType, { observe: 'response' });
  }

  update(oilType: IOilType): Observable<EntityResponseType> {
    return this.http.put<IOilType>(this.resourceUrl, oilType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOilType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOilType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
