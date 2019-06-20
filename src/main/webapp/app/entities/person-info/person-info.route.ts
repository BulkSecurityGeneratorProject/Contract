import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PersonInfo } from 'app/shared/model/person-info.model';
import { PersonInfoService } from './person-info.service';
import { PersonInfoComponent } from './person-info.component';
import { PersonInfoDetailComponent } from './person-info-detail.component';
import { PersonInfoUpdateComponent } from './person-info-update.component';
import { PersonInfoDeletePopupComponent } from './person-info-delete-dialog.component';
import { IPersonInfo } from 'app/shared/model/person-info.model';

@Injectable({ providedIn: 'root' })
export class PersonInfoResolve implements Resolve<IPersonInfo> {
  constructor(private service: PersonInfoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPersonInfo> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PersonInfo>) => response.ok),
        map((personInfo: HttpResponse<PersonInfo>) => personInfo.body)
      );
    }
    return of(new PersonInfo());
  }
}

export const personInfoRoute: Routes = [
  {
    path: '',
    component: PersonInfoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PersonInfos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PersonInfoDetailComponent,
    resolve: {
      personInfo: PersonInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PersonInfos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PersonInfoUpdateComponent,
    resolve: {
      personInfo: PersonInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PersonInfos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PersonInfoUpdateComponent,
    resolve: {
      personInfo: PersonInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PersonInfos'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const personInfoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PersonInfoDeletePopupComponent,
    resolve: {
      personInfo: PersonInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PersonInfos'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
