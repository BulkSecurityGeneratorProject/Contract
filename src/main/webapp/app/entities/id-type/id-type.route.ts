import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IdType } from 'app/shared/model/id-type.model';
import { IdTypeService } from './id-type.service';
import { IdTypeComponent } from './id-type.component';
import { IdTypeDetailComponent } from './id-type-detail.component';
import { IdTypeUpdateComponent } from './id-type-update.component';
import { IdTypeDeletePopupComponent } from './id-type-delete-dialog.component';
import { IIdType } from 'app/shared/model/id-type.model';

@Injectable({ providedIn: 'root' })
export class IdTypeResolve implements Resolve<IIdType> {
  constructor(private service: IdTypeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IIdType> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<IdType>) => response.ok),
        map((idType: HttpResponse<IdType>) => idType.body)
      );
    }
    return of(new IdType());
  }
}

export const idTypeRoute: Routes = [
  {
    path: '',
    component: IdTypeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'IdTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: IdTypeDetailComponent,
    resolve: {
      idType: IdTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'IdTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: IdTypeUpdateComponent,
    resolve: {
      idType: IdTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'IdTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: IdTypeUpdateComponent,
    resolve: {
      idType: IdTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'IdTypes'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const idTypePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: IdTypeDeletePopupComponent,
    resolve: {
      idType: IdTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'IdTypes'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
