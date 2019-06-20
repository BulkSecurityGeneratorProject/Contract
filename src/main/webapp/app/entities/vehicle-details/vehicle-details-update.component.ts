import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IVehicleDetails, VehicleDetails } from 'app/shared/model/vehicle-details.model';
import { VehicleDetailsService } from './vehicle-details.service';

@Component({
  selector: 'jhi-vehicle-details-update',
  templateUrl: './vehicle-details-update.component.html'
})
export class VehicleDetailsUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    plateNumber: [null, [Validators.required]],
    manufactureYear: [null, [Validators.required]],
    color: [null, [Validators.required]],
    operationCardNumber: [null, [Validators.required]],
    operationCardExpiryDate: [null, [Validators.required]],
    insuranceNumber: [null, [Validators.required]],
    insuranceExpiryDate: [null, [Validators.required]],
    additionalInsurance: [],
    oilChangeDate: [null, [Validators.required]],
    oilChangeKmDistance: [null, [Validators.required]],
    availableFuel: [null, [Validators.required]],
    enduranceAmount: [null, [Validators.required]]
  });

  constructor(protected vehicleDetailsService: VehicleDetailsService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ vehicleDetails }) => {
      this.updateForm(vehicleDetails);
    });
  }

  updateForm(vehicleDetails: IVehicleDetails) {
    this.editForm.patchValue({
      id: vehicleDetails.id,
      plateNumber: vehicleDetails.plateNumber,
      manufactureYear: vehicleDetails.manufactureYear,
      color: vehicleDetails.color,
      operationCardNumber: vehicleDetails.operationCardNumber,
      operationCardExpiryDate:
        vehicleDetails.operationCardExpiryDate != null ? vehicleDetails.operationCardExpiryDate.format(DATE_TIME_FORMAT) : null,
      insuranceNumber: vehicleDetails.insuranceNumber,
      insuranceExpiryDate: vehicleDetails.insuranceExpiryDate != null ? vehicleDetails.insuranceExpiryDate.format(DATE_TIME_FORMAT) : null,
      additionalInsurance: vehicleDetails.additionalInsurance,
      oilChangeDate: vehicleDetails.oilChangeDate != null ? vehicleDetails.oilChangeDate.format(DATE_TIME_FORMAT) : null,
      oilChangeKmDistance: vehicleDetails.oilChangeKmDistance,
      availableFuel: vehicleDetails.availableFuel,
      enduranceAmount: vehicleDetails.enduranceAmount
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const vehicleDetails = this.createFromForm();
    if (vehicleDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.vehicleDetailsService.update(vehicleDetails));
    } else {
      this.subscribeToSaveResponse(this.vehicleDetailsService.create(vehicleDetails));
    }
  }

  private createFromForm(): IVehicleDetails {
    const entity = {
      ...new VehicleDetails(),
      id: this.editForm.get(['id']).value,
      plateNumber: this.editForm.get(['plateNumber']).value,
      manufactureYear: this.editForm.get(['manufactureYear']).value,
      color: this.editForm.get(['color']).value,
      operationCardNumber: this.editForm.get(['operationCardNumber']).value,
      operationCardExpiryDate:
        this.editForm.get(['operationCardExpiryDate']).value != null
          ? moment(this.editForm.get(['operationCardExpiryDate']).value, DATE_TIME_FORMAT)
          : undefined,
      insuranceNumber: this.editForm.get(['insuranceNumber']).value,
      insuranceExpiryDate:
        this.editForm.get(['insuranceExpiryDate']).value != null
          ? moment(this.editForm.get(['insuranceExpiryDate']).value, DATE_TIME_FORMAT)
          : undefined,
      additionalInsurance: this.editForm.get(['additionalInsurance']).value,
      oilChangeDate:
        this.editForm.get(['oilChangeDate']).value != null
          ? moment(this.editForm.get(['oilChangeDate']).value, DATE_TIME_FORMAT)
          : undefined,
      oilChangeKmDistance: this.editForm.get(['oilChangeKmDistance']).value,
      availableFuel: this.editForm.get(['availableFuel']).value,
      enduranceAmount: this.editForm.get(['enduranceAmount']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVehicleDetails>>) {
    result.subscribe((res: HttpResponse<IVehicleDetails>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
