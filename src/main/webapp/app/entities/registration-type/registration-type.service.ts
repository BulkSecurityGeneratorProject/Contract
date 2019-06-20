import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRegistrationType } from 'app/shared/model/registration-type.model';

type EntityResponseType = HttpResponse<IRegistrationType>;
type EntityArrayResponseType = HttpResponse<IRegistrationType[]>;

@Injectable({ providedIn: 'root' })
export class RegistrationTypeService {
  public resourceUrl = SERVER_API_URL + 'api/registration-types';

  constructor(protected http: HttpClient) {}

  create(registrationType: IRegistrationType): Observable<EntityResponseType> {
    return this.http.post<IRegistrationType>(this.resourceUrl, registrationType, { observe: 'response' });
  }

  update(registrationType: IRegistrationType): Observable<EntityResponseType> {
    return this.http.put<IRegistrationType>(this.resourceUrl, registrationType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRegistrationType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRegistrationType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
