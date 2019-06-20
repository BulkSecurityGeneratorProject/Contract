import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { LicenseType } from 'app/shared/model/license-type.model';
import { LicenseTypeService } from './license-type.service';
import { LicenseTypeComponent } from './license-type.component';
import { LicenseTypeDetailComponent } from './license-type-detail.component';
import { LicenseTypeUpdateComponent } from './license-type-update.component';
import { LicenseTypeDeletePopupComponent } from './license-type-delete-dialog.component';
import { ILicenseType } from 'app/shared/model/license-type.model';

@Injectable({ providedIn: 'root' })
export class LicenseTypeResolve implements Resolve<ILicenseType> {
  constructor(private service: LicenseTypeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILicenseType> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<LicenseType>) => response.ok),
        map((licenseType: HttpResponse<LicenseType>) => licenseType.body)
      );
    }
    return of(new LicenseType());
  }
}

export const licenseTypeRoute: Routes = [
  {
    path: '',
    component: LicenseTypeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'LicenseTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LicenseTypeDetailComponent,
    resolve: {
      licenseType: LicenseTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'LicenseTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LicenseTypeUpdateComponent,
    resolve: {
      licenseType: LicenseTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'LicenseTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LicenseTypeUpdateComponent,
    resolve: {
      licenseType: LicenseTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'LicenseTypes'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const licenseTypePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: LicenseTypeDeletePopupComponent,
    resolve: {
      licenseType: LicenseTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'LicenseTypes'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
