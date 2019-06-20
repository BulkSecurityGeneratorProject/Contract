import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ContractType } from 'app/shared/model/contract-type.model';
import { ContractTypeService } from './contract-type.service';
import { ContractTypeComponent } from './contract-type.component';
import { ContractTypeDetailComponent } from './contract-type-detail.component';
import { ContractTypeUpdateComponent } from './contract-type-update.component';
import { ContractTypeDeletePopupComponent } from './contract-type-delete-dialog.component';
import { IContractType } from 'app/shared/model/contract-type.model';

@Injectable({ providedIn: 'root' })
export class ContractTypeResolve implements Resolve<IContractType> {
  constructor(private service: ContractTypeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IContractType> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ContractType>) => response.ok),
        map((contractType: HttpResponse<ContractType>) => contractType.body)
      );
    }
    return of(new ContractType());
  }
}

export const contractTypeRoute: Routes = [
  {
    path: '',
    component: ContractTypeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ContractTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ContractTypeDetailComponent,
    resolve: {
      contractType: ContractTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ContractTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ContractTypeUpdateComponent,
    resolve: {
      contractType: ContractTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ContractTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ContractTypeUpdateComponent,
    resolve: {
      contractType: ContractTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ContractTypes'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const contractTypePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ContractTypeDeletePopupComponent,
    resolve: {
      contractType: ContractTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ContractTypes'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
