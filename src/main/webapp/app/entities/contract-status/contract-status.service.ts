import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IContractStatus } from 'app/shared/model/contract-status.model';

type EntityResponseType = HttpResponse<IContractStatus>;
type EntityArrayResponseType = HttpResponse<IContractStatus[]>;

@Injectable({ providedIn: 'root' })
export class ContractStatusService {
  public resourceUrl = SERVER_API_URL + 'api/contract-statuses';

  constructor(protected http: HttpClient) {}

  create(contractStatus: IContractStatus): Observable<EntityResponseType> {
    return this.http.post<IContractStatus>(this.resourceUrl, contractStatus, { observe: 'response' });
  }

  update(contractStatus: IContractStatus): Observable<EntityResponseType> {
    return this.http.put<IContractStatus>(this.resourceUrl, contractStatus, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IContractStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IContractStatus[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
