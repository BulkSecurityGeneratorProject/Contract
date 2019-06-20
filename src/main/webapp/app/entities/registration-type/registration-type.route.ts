import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RegistrationType } from 'app/shared/model/registration-type.model';
import { RegistrationTypeService } from './registration-type.service';
import { RegistrationTypeComponent } from './registration-type.component';
import { RegistrationTypeDetailComponent } from './registration-type-detail.component';
import { RegistrationTypeUpdateComponent } from './registration-type-update.component';
import { RegistrationTypeDeletePopupComponent } from './registration-type-delete-dialog.component';
import { IRegistrationType } from 'app/shared/model/registration-type.model';

@Injectable({ providedIn: 'root' })
export class RegistrationTypeResolve implements Resolve<IRegistrationType> {
  constructor(private service: RegistrationTypeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRegistrationType> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<RegistrationType>) => response.ok),
        map((registrationType: HttpResponse<RegistrationType>) => registrationType.body)
      );
    }
    return of(new RegistrationType());
  }
}

export const registrationTypeRoute: Routes = [
  {
    path: '',
    component: RegistrationTypeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RegistrationTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RegistrationTypeDetailComponent,
    resolve: {
      registrationType: RegistrationTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RegistrationTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RegistrationTypeUpdateComponent,
    resolve: {
      registrationType: RegistrationTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RegistrationTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RegistrationTypeUpdateComponent,
    resolve: {
      registrationType: RegistrationTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RegistrationTypes'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const registrationTypePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: RegistrationTypeDeletePopupComponent,
    resolve: {
      registrationType: RegistrationTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RegistrationTypes'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
