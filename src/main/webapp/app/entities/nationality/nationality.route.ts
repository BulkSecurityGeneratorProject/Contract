import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Nationality } from 'app/shared/model/nationality.model';
import { NationalityService } from './nationality.service';
import { NationalityComponent } from './nationality.component';
import { NationalityDetailComponent } from './nationality-detail.component';
import { NationalityUpdateComponent } from './nationality-update.component';
import { NationalityDeletePopupComponent } from './nationality-delete-dialog.component';
import { INationality } from 'app/shared/model/nationality.model';

@Injectable({ providedIn: 'root' })
export class NationalityResolve implements Resolve<INationality> {
  constructor(private service: NationalityService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<INationality> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Nationality>) => response.ok),
        map((nationality: HttpResponse<Nationality>) => nationality.body)
      );
    }
    return of(new Nationality());
  }
}

export const nationalityRoute: Routes = [
  {
    path: '',
    component: NationalityComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Nationalities'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: NationalityDetailComponent,
    resolve: {
      nationality: NationalityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Nationalities'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: NationalityUpdateComponent,
    resolve: {
      nationality: NationalityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Nationalities'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: NationalityUpdateComponent,
    resolve: {
      nationality: NationalityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Nationalities'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const nationalityPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: NationalityDeletePopupComponent,
    resolve: {
      nationality: NationalityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Nationalities'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
