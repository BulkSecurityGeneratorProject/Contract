import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IRegistrationType, RegistrationType } from 'app/shared/model/registration-type.model';
import { RegistrationTypeService } from './registration-type.service';
import { IVehicleDetails } from 'app/shared/model/vehicle-details.model';
import { VehicleDetailsService } from 'app/entities/vehicle-details';

@Component({
  selector: 'jhi-registration-type-update',
  templateUrl: './registration-type-update.component.html'
})
export class RegistrationTypeUpdateComponent implements OnInit {
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
    protected registrationTypeService: RegistrationTypeService,
    protected vehicleDetailsService: VehicleDetailsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ registrationType }) => {
      this.updateForm(registrationType);
    });
    this.vehicleDetailsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IVehicleDetails[]>) => mayBeOk.ok),
        map((response: HttpResponse<IVehicleDetails[]>) => response.body)
      )
      .subscribe((res: IVehicleDetails[]) => (this.vehicledetails = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(registrationType: IRegistrationType) {
    this.editForm.patchValue({
      id: registrationType.id,
      arName: registrationType.arName,
      enName: registrationType.enName,
      vehicleDetails: registrationType.vehicleDetails
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const registrationType = this.createFromForm();
    if (registrationType.id !== undefined) {
      this.subscribeToSaveResponse(this.registrationTypeService.update(registrationType));
    } else {
      this.subscribeToSaveResponse(this.registrationTypeService.create(registrationType));
    }
  }

  private createFromForm(): IRegistrationType {
    const entity = {
      ...new RegistrationType(),
      id: this.editForm.get(['id']).value,
      arName: this.editForm.get(['arName']).value,
      enName: this.editForm.get(['enName']).value,
      vehicleDetails: this.editForm.get(['vehicleDetails']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRegistrationType>>) {
    result.subscribe((res: HttpResponse<IRegistrationType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
