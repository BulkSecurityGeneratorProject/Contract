import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IVehicleItemStatus, VehicleItemStatus } from 'app/shared/model/vehicle-item-status.model';
import { VehicleItemStatusService } from './vehicle-item-status.service';

@Component({
  selector: 'jhi-vehicle-item-status-update',
  templateUrl: './vehicle-item-status-update.component.html'
})
export class VehicleItemStatusUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    ac: [null, [Validators.required]],
    radioStereo: [null, [Validators.required]],
    screen: [null, [Validators.required]],
    speedometer: [null, [Validators.required]],
    carSeats: [null, [Validators.required]],
    tires: [null, [Validators.required]],
    spareTire: [null, [Validators.required]],
    spareTireTools: [null, [Validators.required]],
    firtsAidKit: [null, [Validators.required]],
    key: [null, [Validators.required]],
    fireExtinguisher: [null, [Validators.required]],
    safetyTriangle: [null, [Validators.required]]
  });

  constructor(
    protected vehicleItemStatusService: VehicleItemStatusService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ vehicleItemStatus }) => {
      this.updateForm(vehicleItemStatus);
    });
  }

  updateForm(vehicleItemStatus: IVehicleItemStatus) {
    this.editForm.patchValue({
      id: vehicleItemStatus.id,
      ac: vehicleItemStatus.ac,
      radioStereo: vehicleItemStatus.radioStereo,
      screen: vehicleItemStatus.screen,
      speedometer: vehicleItemStatus.speedometer,
      carSeats: vehicleItemStatus.carSeats,
      tires: vehicleItemStatus.tires,
      spareTire: vehicleItemStatus.spareTire,
      spareTireTools: vehicleItemStatus.spareTireTools,
      firtsAidKit: vehicleItemStatus.firtsAidKit,
      key: vehicleItemStatus.key,
      fireExtinguisher: vehicleItemStatus.fireExtinguisher,
      safetyTriangle: vehicleItemStatus.safetyTriangle
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const vehicleItemStatus = this.createFromForm();
    if (vehicleItemStatus.id !== undefined) {
      this.subscribeToSaveResponse(this.vehicleItemStatusService.update(vehicleItemStatus));
    } else {
      this.subscribeToSaveResponse(this.vehicleItemStatusService.create(vehicleItemStatus));
    }
  }

  private createFromForm(): IVehicleItemStatus {
    const entity = {
      ...new VehicleItemStatus(),
      id: this.editForm.get(['id']).value,
      ac: this.editForm.get(['ac']).value,
      radioStereo: this.editForm.get(['radioStereo']).value,
      screen: this.editForm.get(['screen']).value,
      speedometer: this.editForm.get(['speedometer']).value,
      carSeats: this.editForm.get(['carSeats']).value,
      tires: this.editForm.get(['tires']).value,
      spareTire: this.editForm.get(['spareTire']).value,
      spareTireTools: this.editForm.get(['spareTireTools']).value,
      firtsAidKit: this.editForm.get(['firtsAidKit']).value,
      key: this.editForm.get(['key']).value,
      fireExtinguisher: this.editForm.get(['fireExtinguisher']).value,
      safetyTriangle: this.editForm.get(['safetyTriangle']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVehicleItemStatus>>) {
    result.subscribe((res: HttpResponse<IVehicleItemStatus>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
