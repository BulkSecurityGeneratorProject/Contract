import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RentContract } from 'app/shared/model/rent-contract.model';
import { RentContractService } from './rent-contract.service';
import { RentContractComponent } from './rent-contract.component';
import { RentContractDetailComponent } from './rent-contract-detail.component';
import { RentContractUpdateComponent } from './rent-contract-update.component';
import { RentContractDeletePopupComponent } from './rent-contract-delete-dialog.component';
import { IRentContract } from 'app/shared/model/rent-contract.model';

@Injectable({ providedIn: 'root' })
export class RentContractResolve implements Resolve<IRentContract> {
  constructor(private service: RentContractService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRentContract> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<RentContract>) => response.ok),
        map((rentContract: HttpResponse<RentContract>) => rentContract.body)
      );
    }
    return of(new RentContract());
  }
}

export const rentContractRoute: Routes = [
  {
    path: '',
    component: RentContractComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'RentContracts'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RentContractDetailComponent,
    resolve: {
      rentContract: RentContractResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RentContracts'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RentContractUpdateComponent,
    resolve: {
      rentContract: RentContractResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RentContracts'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RentContractUpdateComponent,
    resolve: {
      rentContract: RentContractResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RentContracts'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const rentContractPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: RentContractDeletePopupComponent,
    resolve: {
      rentContract: RentContractResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RentContracts'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
