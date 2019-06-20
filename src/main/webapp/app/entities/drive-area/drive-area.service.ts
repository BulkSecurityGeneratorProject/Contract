import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDriveArea } from 'app/shared/model/drive-area.model';

type EntityResponseType = HttpResponse<IDriveArea>;
type EntityArrayResponseType = HttpResponse<IDriveArea[]>;

@Injectable({ providedIn: 'root' })
export class DriveAreaService {
  public resourceUrl = SERVER_API_URL + 'api/drive-areas';

  constructor(protected http: HttpClient) {}

  create(driveArea: IDriveArea): Observable<EntityResponseType> {
    return this.http.post<IDriveArea>(this.resourceUrl, driveArea, { observe: 'response' });
  }

  update(driveArea: IDriveArea): Observable<EntityResponseType> {
    return this.http.put<IDriveArea>(this.resourceUrl, driveArea, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDriveArea>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDriveArea[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
