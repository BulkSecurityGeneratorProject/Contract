import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IIdType } from 'app/shared/model/id-type.model';

type EntityResponseType = HttpResponse<IIdType>;
type EntityArrayResponseType = HttpResponse<IIdType[]>;

@Injectable({ providedIn: 'root' })
export class IdTypeService {
  public resourceUrl = SERVER_API_URL + 'api/id-types';

  constructor(protected http: HttpClient) {}

  create(idType: IIdType): Observable<EntityResponseType> {
    return this.http.post<IIdType>(this.resourceUrl, idType, { observe: 'response' });
  }

  update(idType: IIdType): Observable<EntityResponseType> {
    return this.http.put<IIdType>(this.resourceUrl, idType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IIdType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IIdType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
