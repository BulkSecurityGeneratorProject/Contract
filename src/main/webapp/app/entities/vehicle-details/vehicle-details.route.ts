import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { VehicleDetails } from 'app/shared/model/vehicle-details.model';
import { VehicleDetailsService } from './vehicle-details.service';
import { VehicleDetailsComponent } from './vehicle-details.component';
import { VehicleDetailsDetailComponent } from './vehicle-details-detail.component';
import { VehicleDetailsUpdateComponent } from './vehicle-details-update.component';
import { VehicleDetailsDeletePopupComponent } from './vehicle-details-delete-dialog.component';
import { IVehicleDetails } from 'app/shared/model/vehicle-details.model';

@Injectable({ providedIn: 'root' })
export class VehicleDetailsResolve implements Resolve<IVehicleDetails> {
  constructor(private service: VehicleDetailsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IVehicleDetails> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<VehicleDetails>) => response.ok),
        map((vehicleDetails: HttpResponse<VehicleDetails>) => vehicleDetails.body)
      );
    }
    return of(new VehicleDetails());
  }
}

export const vehicleDetailsRoute: Routes = [
  {
    path: '',
    component: VehicleDetailsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'VehicleDetails'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: VehicleDetailsDetailComponent,
    resolve: {
      vehicleDetails: VehicleDetailsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'VehicleDetails'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: VehicleDetailsUpdateComponent,
    resolve: {
      vehicleDetails: VehicleDetailsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'VehicleDetails'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: VehicleDetailsUpdateComponent,
    resolve: {
      vehicleDetails: VehicleDetailsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'VehicleDetails'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const vehicleDetailsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: VehicleDetailsDeletePopupComponent,
    resolve: {
      vehicleDetails: VehicleDetailsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'VehicleDetails'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
