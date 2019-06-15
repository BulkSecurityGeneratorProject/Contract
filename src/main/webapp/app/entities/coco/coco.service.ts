import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICOCO } from 'app/shared/model/coco.model';

type EntityResponseType = HttpResponse<ICOCO>;
type EntityArrayResponseType = HttpResponse<ICOCO[]>;

@Injectable({ providedIn: 'root' })
export class COCOService {
  public resourceUrl = SERVER_API_URL + 'api/cocos';

  constructor(protected http: HttpClient) {}

  create(cOCO: ICOCO): Observable<EntityResponseType> {
    return this.http.post<ICOCO>(this.resourceUrl, cOCO, { observe: 'response' });
  }

  update(cOCO: ICOCO): Observable<EntityResponseType> {
    return this.http.put<ICOCO>(this.resourceUrl, cOCO, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICOCO>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICOCO[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
