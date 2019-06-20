import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ContractStatus } from 'app/shared/model/contract-status.model';
import { ContractStatusService } from './contract-status.service';
import { ContractStatusComponent } from './contract-status.component';
import { ContractStatusDetailComponent } from './contract-status-detail.component';
import { ContractStatusUpdateComponent } from './contract-status-update.component';
import { ContractStatusDeletePopupComponent } from './contract-status-delete-dialog.component';
import { IContractStatus } from 'app/shared/model/contract-status.model';

@Injectable({ providedIn: 'root' })
export class ContractStatusResolve implements Resolve<IContractStatus> {
  constructor(private service: ContractStatusService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IContractStatus> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ContractStatus>) => response.ok),
        map((contractStatus: HttpResponse<ContractStatus>) => contractStatus.body)
      );
    }
    return of(new ContractStatus());
  }
}

export const contractStatusRoute: Routes = [
  {
    path: '',
    component: ContractStatusComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ContractStatuses'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ContractStatusDetailComponent,
    resolve: {
      contractStatus: ContractStatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ContractStatuses'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ContractStatusUpdateComponent,
    resolve: {
      contractStatus: ContractStatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ContractStatuses'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ContractStatusUpdateComponent,
    resolve: {
      contractStatus: ContractStatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ContractStatuses'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const contractStatusPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ContractStatusDeletePopupComponent,
    resolve: {
      contractStatus: ContractStatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ContractStatuses'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
