import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { FuelType } from 'app/shared/model/fuel-type.model';
import { FuelTypeService } from './fuel-type.service';
import { FuelTypeComponent } from './fuel-type.component';
import { FuelTypeDetailComponent } from './fuel-type-detail.component';
import { FuelTypeUpdateComponent } from './fuel-type-update.component';
import { FuelTypeDeletePopupComponent } from './fuel-type-delete-dialog.component';
import { IFuelType } from 'app/shared/model/fuel-type.model';

@Injectable({ providedIn: 'root' })
export class FuelTypeResolve implements Resolve<IFuelType> {
  constructor(private service: FuelTypeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFuelType> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<FuelType>) => response.ok),
        map((fuelType: HttpResponse<FuelType>) => fuelType.body)
      );
    }
    return of(new FuelType());
  }
}

export const fuelTypeRoute: Routes = [
  {
    path: '',
    component: FuelTypeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'FuelTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FuelTypeDetailComponent,
    resolve: {
      fuelType: FuelTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'FuelTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FuelTypeUpdateComponent,
    resolve: {
      fuelType: FuelTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'FuelTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FuelTypeUpdateComponent,
    resolve: {
      fuelType: FuelTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'FuelTypes'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const fuelTypePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FuelTypeDeletePopupComponent,
    resolve: {
      fuelType: FuelTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'FuelTypes'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
