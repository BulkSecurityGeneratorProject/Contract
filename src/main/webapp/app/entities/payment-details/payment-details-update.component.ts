import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IPaymentDetails, PaymentDetails } from 'app/shared/model/payment-details.model';
import { PaymentDetailsService } from './payment-details.service';

@Component({
  selector: 'jhi-payment-details-update',
  templateUrl: './payment-details-update.component.html'
})
export class PaymentDetailsUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    total: [null, [Validators.required]],
    totalRentCost: [null, [Validators.required]],
    extraKmCost: [null, [Validators.required]],
    driverCost: [null, [Validators.required]],
    internationalAuthorizationCost: [null, [Validators.required]],
    vehicleTransferCost: [null, [Validators.required]],
    sparePartsCost: [null, [Validators.required]],
    oilChangeCost: [null, [Validators.required]],
    damageCost: [null, [Validators.required]],
    fuelCost: [null, [Validators.required]],
    discountPercentage: [null, [Validators.required]],
    discount: [null, [Validators.required]],
    vat: [null, [Validators.required]],
    paid: [null, [Validators.required]],
    remaining: [null, [Validators.required]]
  });

  constructor(protected paymentDetailsService: PaymentDetailsService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ paymentDetails }) => {
      this.updateForm(paymentDetails);
    });
  }

  updateForm(paymentDetails: IPaymentDetails) {
    this.editForm.patchValue({
      id: paymentDetails.id,
      total: paymentDetails.total,
      totalRentCost: paymentDetails.totalRentCost,
      extraKmCost: paymentDetails.extraKmCost,
      driverCost: paymentDetails.driverCost,
      internationalAuthorizationCost: paymentDetails.internationalAuthorizationCost,
      vehicleTransferCost: paymentDetails.vehicleTransferCost,
      sparePartsCost: paymentDetails.sparePartsCost,
      oilChangeCost: paymentDetails.oilChangeCost,
      damageCost: paymentDetails.damageCost,
      fuelCost: paymentDetails.fuelCost,
      discountPercentage: paymentDetails.discountPercentage,
      discount: paymentDetails.discount,
      vat: paymentDetails.vat,
      paid: paymentDetails.paid,
      remaining: paymentDetails.remaining
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const paymentDetails = this.createFromForm();
    if (paymentDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.paymentDetailsService.update(paymentDetails));
    } else {
      this.subscribeToSaveResponse(this.paymentDetailsService.create(paymentDetails));
    }
  }

  private createFromForm(): IPaymentDetails {
    const entity = {
      ...new PaymentDetails(),
      id: this.editForm.get(['id']).value,
      total: this.editForm.get(['total']).value,
      totalRentCost: this.editForm.get(['totalRentCost']).value,
      extraKmCost: this.editForm.get(['extraKmCost']).value,
      driverCost: this.editForm.get(['driverCost']).value,
      internationalAuthorizationCost: this.editForm.get(['internationalAuthorizationCost']).value,
      vehicleTransferCost: this.editForm.get(['vehicleTransferCost']).value,
      sparePartsCost: this.editForm.get(['sparePartsCost']).value,
      oilChangeCost: this.editForm.get(['oilChangeCost']).value,
      damageCost: this.editForm.get(['damageCost']).value,
      fuelCost: this.editForm.get(['fuelCost']).value,
      discountPercentage: this.editForm.get(['discountPercentage']).value,
      discount: this.editForm.get(['discount']).value,
      vat: this.editForm.get(['vat']).value,
      paid: this.editForm.get(['paid']).value,
      remaining: this.editForm.get(['remaining']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaymentDetails>>) {
    result.subscribe((res: HttpResponse<IPaymentDetails>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
