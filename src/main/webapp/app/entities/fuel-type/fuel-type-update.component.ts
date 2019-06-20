import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IFuelType, FuelType } from 'app/shared/model/fuel-type.model';
import { FuelTypeService } from './fuel-type.service';
import { IVehicleDetails } from 'app/shared/model/vehicle-details.model';
import { VehicleDetailsService } from 'app/entities/vehicle-details';

@Component({
  selector: 'jhi-fuel-type-update',
  templateUrl: './fuel-type-update.component.html'
})
export class FuelTypeUpdateComponent implements OnInit {
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
    protected fuelTypeService: FuelTypeService,
    protected vehicleDetailsService: VehicleDetailsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ fuelType }) => {
      this.updateForm(fuelType);
    });
    this.vehicleDetailsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IVehicleDetails[]>) => mayBeOk.ok),
        map((response: HttpResponse<IVehicleDetails[]>) => response.body)
      )
      .subscribe((res: IVehicleDetails[]) => (this.vehicledetails = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(fuelType: IFuelType) {
    this.editForm.patchValue({
      id: fuelType.id,
      arName: fuelType.arName,
      enName: fuelType.enName,
      vehicleDetails: fuelType.vehicleDetails
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const fuelType = this.createFromForm();
    if (fuelType.id !== undefined) {
      this.subscribeToSaveResponse(this.fuelTypeService.update(fuelType));
    } else {
      this.subscribeToSaveResponse(this.fuelTypeService.create(fuelType));
    }
  }

  private createFromForm(): IFuelType {
    const entity = {
      ...new FuelType(),
      id: this.editForm.get(['id']).value,
      arName: this.editForm.get(['arName']).value,
      enName: this.editForm.get(['enName']).value,
      vehicleDetails: this.editForm.get(['vehicleDetails']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFuelType>>) {
    result.subscribe((res: HttpResponse<IFuelType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
