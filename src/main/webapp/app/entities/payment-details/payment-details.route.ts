import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PaymentDetails } from 'app/shared/model/payment-details.model';
import { PaymentDetailsService } from './payment-details.service';
import { PaymentDetailsComponent } from './payment-details.component';
import { PaymentDetailsDetailComponent } from './payment-details-detail.component';
import { PaymentDetailsUpdateComponent } from './payment-details-update.component';
import { PaymentDetailsDeletePopupComponent } from './payment-details-delete-dialog.component';
import { IPaymentDetails } from 'app/shared/model/payment-details.model';

@Injectable({ providedIn: 'root' })
export class PaymentDetailsResolve implements Resolve<IPaymentDetails> {
  constructor(private service: PaymentDetailsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPaymentDetails> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PaymentDetails>) => response.ok),
        map((paymentDetails: HttpResponse<PaymentDetails>) => paymentDetails.body)
      );
    }
    return of(new PaymentDetails());
  }
}

export const paymentDetailsRoute: Routes = [
  {
    path: '',
    component: PaymentDetailsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PaymentDetails'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PaymentDetailsDetailComponent,
    resolve: {
      paymentDetails: PaymentDetailsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PaymentDetails'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PaymentDetailsUpdateComponent,
    resolve: {
      paymentDetails: PaymentDetailsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PaymentDetails'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PaymentDetailsUpdateComponent,
    resolve: {
      paymentDetails: PaymentDetailsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PaymentDetails'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const paymentDetailsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PaymentDetailsDeletePopupComponent,
    resolve: {
      paymentDetails: PaymentDetailsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PaymentDetails'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
