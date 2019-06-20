import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RentalAccount } from 'app/shared/model/rental-account.model';
import { RentalAccountService } from './rental-account.service';
import { RentalAccountComponent } from './rental-account.component';
import { RentalAccountDetailComponent } from './rental-account-detail.component';
import { RentalAccountUpdateComponent } from './rental-account-update.component';
import { RentalAccountDeletePopupComponent } from './rental-account-delete-dialog.component';
import { IRentalAccount } from 'app/shared/model/rental-account.model';

@Injectable({ providedIn: 'root' })
export class RentalAccountResolve implements Resolve<IRentalAccount> {
  constructor(private service: RentalAccountService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRentalAccount> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<RentalAccount>) => response.ok),
        map((rentalAccount: HttpResponse<RentalAccount>) => rentalAccount.body)
      );
    }
    return of(new RentalAccount());
  }
}

export const rentalAccountRoute: Routes = [
  {
    path: '',
    component: RentalAccountComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RentalAccounts'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RentalAccountDetailComponent,
    resolve: {
      rentalAccount: RentalAccountResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RentalAccounts'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RentalAccountUpdateComponent,
    resolve: {
      rentalAccount: RentalAccountResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RentalAccounts'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RentalAccountUpdateComponent,
    resolve: {
      rentalAccount: RentalAccountResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RentalAccounts'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const rentalAccountPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: RentalAccountDeletePopupComponent,
    resolve: {
      rentalAccount: RentalAccountResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'RentalAccounts'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
