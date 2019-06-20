import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { VehicleType } from 'app/shared/model/vehicle-type.model';
import { VehicleTypeService } from './vehicle-type.service';
import { VehicleTypeComponent } from './vehicle-type.component';
import { VehicleTypeDetailComponent } from './vehicle-type-detail.component';
import { VehicleTypeUpdateComponent } from './vehicle-type-update.component';
import { VehicleTypeDeletePopupComponent } from './vehicle-type-delete-dialog.component';
import { IVehicleType } from 'app/shared/model/vehicle-type.model';

@Injectable({ providedIn: 'root' })
export class VehicleTypeResolve implements Resolve<IVehicleType> {
  constructor(private service: VehicleTypeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IVehicleType> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<VehicleType>) => response.ok),
        map((vehicleType: HttpResponse<VehicleType>) => vehicleType.body)
      );
    }
    return of(new VehicleType());
  }
}

export const vehicleTypeRoute: Routes = [
  {
    path: '',
    component: VehicleTypeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'VehicleTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: VehicleTypeDetailComponent,
    resolve: {
      vehicleType: VehicleTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'VehicleTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: VehicleTypeUpdateComponent,
    resolve: {
      vehicleType: VehicleTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'VehicleTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: VehicleTypeUpdateComponent,
    resolve: {
      vehicleType: VehicleTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'VehicleTypes'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const vehicleTypePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: VehicleTypeDeletePopupComponent,
    resolve: {
      vehicleType: VehicleTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'VehicleTypes'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
