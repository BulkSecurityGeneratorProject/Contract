import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPaymentMethod, PaymentMethod } from 'app/shared/model/payment-method.model';
import { PaymentMethodService } from './payment-method.service';
import { IPaymentDetails } from 'app/shared/model/payment-details.model';
import { PaymentDetailsService } from 'app/entities/payment-details';

@Component({
  selector: 'jhi-payment-method-update',
  templateUrl: './payment-method-update.component.html'
})
export class PaymentMethodUpdateComponent implements OnInit {
  isSaving: boolean;

  paymentdetails: IPaymentDetails[];

  editForm = this.fb.group({
    id: [],
    arName: [],
    enName: [],
    paymentDetails: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected paymentMethodService: PaymentMethodService,
    protected paymentDetailsService: PaymentDetailsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ paymentMethod }) => {
      this.updateForm(paymentMethod);
    });
    this.paymentDetailsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPaymentDetails[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPaymentDetails[]>) => response.body)
      )
      .subscribe((res: IPaymentDetails[]) => (this.paymentdetails = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(paymentMethod: IPaymentMethod) {
    this.editForm.patchValue({
      id: paymentMethod.id,
      arName: paymentMethod.arName,
      enName: paymentMethod.enName,
      paymentDetails: paymentMethod.paymentDetails
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const paymentMethod = this.createFromForm();
    if (paymentMethod.id !== undefined) {
      this.subscribeToSaveResponse(this.paymentMethodService.update(paymentMethod));
    } else {
      this.subscribeToSaveResponse(this.paymentMethodService.create(paymentMethod));
    }
  }

  private createFromForm(): IPaymentMethod {
    const entity = {
      ...new PaymentMethod(),
      id: this.editForm.get(['id']).value,
      arName: this.editForm.get(['arName']).value,
      enName: this.editForm.get(['enName']).value,
      paymentDetails: this.editForm.get(['paymentDetails']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaymentMethod>>) {
    result.subscribe((res: HttpResponse<IPaymentMethod>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackPaymentDetailsById(index: number, item: IPaymentDetails) {
    return item.id;
  }
}
