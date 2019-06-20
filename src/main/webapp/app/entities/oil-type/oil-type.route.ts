import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { OilType } from 'app/shared/model/oil-type.model';
import { OilTypeService } from './oil-type.service';
import { OilTypeComponent } from './oil-type.component';
import { OilTypeDetailComponent } from './oil-type-detail.component';
import { OilTypeUpdateComponent } from './oil-type-update.component';
import { OilTypeDeletePopupComponent } from './oil-type-delete-dialog.component';
import { IOilType } from 'app/shared/model/oil-type.model';

@Injectable({ providedIn: 'root' })
export class OilTypeResolve implements Resolve<IOilType> {
  constructor(private service: OilTypeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IOilType> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<OilType>) => response.ok),
        map((oilType: HttpResponse<OilType>) => oilType.body)
      );
    }
    return of(new OilType());
  }
}

export const oilTypeRoute: Routes = [
  {
    path: '',
    component: OilTypeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'OilTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OilTypeDetailComponent,
    resolve: {
      oilType: OilTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'OilTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OilTypeUpdateComponent,
    resolve: {
      oilType: OilTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'OilTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OilTypeUpdateComponent,
    resolve: {
      oilType: OilTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'OilTypes'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const oilTypePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: OilTypeDeletePopupComponent,
    resolve: {
      oilType: OilTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'OilTypes'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
