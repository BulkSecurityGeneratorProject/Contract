import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DriveArea } from 'app/shared/model/drive-area.model';
import { DriveAreaService } from './drive-area.service';
import { DriveAreaComponent } from './drive-area.component';
import { DriveAreaDetailComponent } from './drive-area-detail.component';
import { DriveAreaUpdateComponent } from './drive-area-update.component';
import { DriveAreaDeletePopupComponent } from './drive-area-delete-dialog.component';
import { IDriveArea } from 'app/shared/model/drive-area.model';

@Injectable({ providedIn: 'root' })
export class DriveAreaResolve implements Resolve<IDriveArea> {
  constructor(private service: DriveAreaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDriveArea> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<DriveArea>) => response.ok),
        map((driveArea: HttpResponse<DriveArea>) => driveArea.body)
      );
    }
    return of(new DriveArea());
  }
}

export const driveAreaRoute: Routes = [
  {
    path: '',
    component: DriveAreaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DriveAreas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DriveAreaDetailComponent,
    resolve: {
      driveArea: DriveAreaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DriveAreas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DriveAreaUpdateComponent,
    resolve: {
      driveArea: DriveAreaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DriveAreas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DriveAreaUpdateComponent,
    resolve: {
      driveArea: DriveAreaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DriveAreas'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const driveAreaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DriveAreaDeletePopupComponent,
    resolve: {
      driveArea: DriveAreaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DriveAreas'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
