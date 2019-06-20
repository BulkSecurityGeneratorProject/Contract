import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { InsuranceType } from 'app/shared/model/insurance-type.model';
import { InsuranceTypeService } from './insurance-type.service';
import { InsuranceTypeComponent } from './insurance-type.component';
import { InsuranceTypeDetailComponent } from './insurance-type-detail.component';
import { InsuranceTypeUpdateComponent } from './insurance-type-update.component';
import { InsuranceTypeDeletePopupComponent } from './insurance-type-delete-dialog.component';
import { IInsuranceType } from 'app/shared/model/insurance-type.model';

@Injectable({ providedIn: 'root' })
export class InsuranceTypeResolve implements Resolve<IInsuranceType> {
  constructor(private service: InsuranceTypeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IInsuranceType> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<InsuranceType>) => response.ok),
        map((insuranceType: HttpResponse<InsuranceType>) => insuranceType.body)
      );
    }
    return of(new InsuranceType());
  }
}

export const insuranceTypeRoute: Routes = [
  {
    path: '',
    component: InsuranceTypeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'InsuranceTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InsuranceTypeDetailComponent,
    resolve: {
      insuranceType: InsuranceTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'InsuranceTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InsuranceTypeUpdateComponent,
    resolve: {
      insuranceType: InsuranceTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'InsuranceTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InsuranceTypeUpdateComponent,
    resolve: {
      insuranceType: InsuranceTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'InsuranceTypes'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const insuranceTypePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: InsuranceTypeDeletePopupComponent,
    resolve: {
      insuranceType: InsuranceTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'InsuranceTypes'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
