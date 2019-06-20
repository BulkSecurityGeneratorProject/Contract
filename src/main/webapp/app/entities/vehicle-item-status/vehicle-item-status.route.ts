import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { VehicleItemStatus } from 'app/shared/model/vehicle-item-status.model';
import { VehicleItemStatusService } from './vehicle-item-status.service';
import { VehicleItemStatusComponent } from './vehicle-item-status.component';
import { VehicleItemStatusDetailComponent } from './vehicle-item-status-detail.component';
import { VehicleItemStatusUpdateComponent } from './vehicle-item-status-update.component';
import { VehicleItemStatusDeletePopupComponent } from './vehicle-item-status-delete-dialog.component';
import { IVehicleItemStatus } from 'app/shared/model/vehicle-item-status.model';

@Injectable({ providedIn: 'root' })
export class VehicleItemStatusResolve implements Resolve<IVehicleItemStatus> {
  constructor(private service: VehicleItemStatusService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IVehicleItemStatus> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<VehicleItemStatus>) => response.ok),
        map((vehicleItemStatus: HttpResponse<VehicleItemStatus>) => vehicleItemStatus.body)
      );
    }
    return of(new VehicleItemStatus());
  }
}

export const vehicleItemStatusRoute: Routes = [
  {
    path: '',
    component: VehicleItemStatusComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'VehicleItemStatuses'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: VehicleItemStatusDetailComponent,
    resolve: {
      vehicleItemStatus: VehicleItemStatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'VehicleItemStatuses'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: VehicleItemStatusUpdateComponent,
    resolve: {
      vehicleItemStatus: VehicleItemStatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'VehicleItemStatuses'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: VehicleItemStatusUpdateComponent,
    resolve: {
      vehicleItemStatus: VehicleItemStatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'VehicleItemStatuses'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const vehicleItemStatusPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: VehicleItemStatusDeletePopupComponent,
    resolve: {
      vehicleItemStatus: VehicleItemStatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'VehicleItemStatuses'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
