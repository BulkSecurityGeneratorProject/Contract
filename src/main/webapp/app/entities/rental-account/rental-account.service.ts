import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRentalAccount } from 'app/shared/model/rental-account.model';

type EntityResponseType = HttpResponse<IRentalAccount>;
type EntityArrayResponseType = HttpResponse<IRentalAccount[]>;

@Injectable({ providedIn: 'root' })
export class RentalAccountService {
  public resourceUrl = SERVER_API_URL + 'api/rental-accounts';

  constructor(protected http: HttpClient) {}

  create(rentalAccount: IRentalAccount): Observable<EntityResponseType> {
    return this.http.post<IRentalAccount>(this.resourceUrl, rentalAccount, { observe: 'response' });
  }

  update(rentalAccount: IRentalAccount): Observable<EntityResponseType> {
    return this.http.put<IRentalAccount>(this.resourceUrl, rentalAccount, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRentalAccount>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRentalAccount[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
