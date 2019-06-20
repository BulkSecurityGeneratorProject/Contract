import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IVehicleType, VehicleType } from 'app/shared/model/vehicle-type.model';
import { VehicleTypeService } from './vehicle-type.service';
import { IVehicleDetails } from 'app/shared/model/vehicle-details.model';
import { VehicleDetailsService } from 'app/entities/vehicle-details';

@Component({
  selector: 'jhi-vehicle-type-update',
  templateUrl: './vehicle-type-update.component.html'
})
export class VehicleTypeUpdateComponent implements OnInit {
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
    protected vehicleTypeService: VehicleTypeService,
    protected vehicleDetailsService: VehicleDetailsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ vehicleType }) => {
      this.updateForm(vehicleType);
    });
    this.vehicleDetailsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IVehicleDetails[]>) => mayBeOk.ok),
        map((response: HttpResponse<IVehicleDetails[]>) => response.body)
      )
      .subscribe((res: IVehicleDetails[]) => (this.vehicledetails = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(vehicleType: IVehicleType) {
    this.editForm.patchValue({
      id: vehicleType.id,
      arName: vehicleType.arName,
      enName: vehicleType.enName,
      vehicleDetails: vehicleType.vehicleDetails
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const vehicleType = this.createFromForm();
    if (vehicleType.id !== undefined) {
      this.subscribeToSaveResponse(this.vehicleTypeService.update(vehicleType));
    } else {
      this.subscribeToSaveResponse(this.vehicleTypeService.create(vehicleType));
    }
  }

  private createFromForm(): IVehicleType {
    const entity = {
      ...new VehicleType(),
      id: this.editForm.get(['id']).value,
      arName: this.editForm.get(['arName']).value,
      enName: this.editForm.get(['enName']).value,
      vehicleDetails: this.editForm.get(['vehicleDetails']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVehicleType>>) {
    result.subscribe((res: HttpResponse<IVehicleType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
