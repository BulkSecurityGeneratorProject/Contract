import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IInsuranceType, InsuranceType } from 'app/shared/model/insurance-type.model';
import { InsuranceTypeService } from './insurance-type.service';
import { IVehicleDetails } from 'app/shared/model/vehicle-details.model';
import { VehicleDetailsService } from 'app/entities/vehicle-details';

@Component({
  selector: 'jhi-insurance-type-update',
  templateUrl: './insurance-type-update.component.html'
})
export class InsuranceTypeUpdateComponent implements OnInit {
  isSaving: boolean;

  vehicledetails: IVehicleDetails[];

  editForm = this.fb.group({
    id: [],
    arName: [],
    enName: [],
    vehicleDetails: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected insuranceTypeService: InsuranceTypeService,
    protected vehicleDetailsService: VehicleDetailsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ insuranceType }) => {
      this.updateForm(insuranceType);
    });
    this.vehicleDetailsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IVehicleDetails[]>) => mayBeOk.ok),
        map((response: HttpResponse<IVehicleDetails[]>) => response.body)
      )
      .subscribe((res: IVehicleDetails[]) => (this.vehicledetails = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(insuranceType: IInsuranceType) {
    this.editForm.patchValue({
      id: insuranceType.id,
      arName: insuranceType.arName,
      enName: insuranceType.enName,
      vehicleDetails: insuranceType.vehicleDetails
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const insuranceType = this.createFromForm();
    if (insuranceType.id !== undefined) {
      this.subscribeToSaveResponse(this.insuranceTypeService.update(insuranceType));
    } else {
      this.subscribeToSaveResponse(this.insuranceTypeService.create(insuranceType));
    }
  }

  private createFromForm(): IInsuranceType {
    const entity = {
      ...new InsuranceType(),
      id: this.editForm.get(['id']).value,
      arName: this.editForm.get(['arName']).value,
      enName: this.editForm.get(['enName']).value,
      vehicleDetails: this.editForm.get(['vehicleDetails']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInsuranceType>>) {
    result.subscribe((res: HttpResponse<IInsuranceType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackVehicleDetailsById(index: number, item: IVehicleDetails) {
    return item.id;
  }
}
