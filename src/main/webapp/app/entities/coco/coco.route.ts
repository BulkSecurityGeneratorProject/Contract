import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { COCO } from 'app/shared/model/coco.model';
import { COCOService } from './coco.service';
import { COCOComponent } from './coco.component';
import { COCODetailComponent } from './coco-detail.component';
import { COCOUpdateComponent } from './coco-update.component';
import { COCODeletePopupComponent } from './coco-delete-dialog.component';
import { ICOCO } from 'app/shared/model/coco.model';

@Injectable({ providedIn: 'root' })
export class COCOResolve implements Resolve<ICOCO> {
  constructor(private service: COCOService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICOCO> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<COCO>) => response.ok),
        map((cOCO: HttpResponse<COCO>) => cOCO.body)
      );
    }
    return of(new COCO());
  }
}

export const cOCORoute: Routes = [
  {
    path: '',
    component: COCOComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'COCOS'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: COCODetailComponent,
    resolve: {
      cOCO: COCOResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'COCOS'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: COCOUpdateComponent,
    resolve: {
      cOCO: COCOResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'COCOS'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: COCOUpdateComponent,
    resolve: {
      cOCO: COCOResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'COCOS'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const cOCOPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: COCODeletePopupComponent,
    resolve: {
      cOCO: COCOResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'COCOS'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
