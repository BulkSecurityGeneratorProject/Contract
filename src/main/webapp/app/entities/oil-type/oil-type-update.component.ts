import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IOilType, OilType } from 'app/shared/model/oil-type.model';
import { OilTypeService } from './oil-type.service';
import { IVehicleDetails } from 'app/shared/model/vehicle-details.model';
import { VehicleDetailsService } from 'app/entities/vehicle-details';

@Component({
  selector: 'jhi-oil-type-update',
  templateUrl: './oil-type-update.component.html'
})
export class OilTypeUpdateComponent implements OnInit {
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
    protected oilTypeService: OilTypeService,
    protected vehicleDetailsService: VehicleDetailsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ oilType }) => {
      this.updateForm(oilType);
    });
    this.vehicleDetailsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IVehicleDetails[]>) => mayBeOk.ok),
        map((response: HttpResponse<IVehicleDetails[]>) => response.body)
      )
      .subscribe((res: IVehicleDetails[]) => (this.vehicledetails = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(oilType: IOilType) {
    this.editForm.patchValue({
      id: oilType.id,
      arName: oilType.arName,
      enName: oilType.enName,
      vehicleDetails: oilType.vehicleDetails
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const oilType = this.createFromForm();
    if (oilType.id !== undefined) {
      this.subscribeToSaveResponse(this.oilTypeService.update(oilType));
    } else {
      this.subscribeToSaveResponse(this.oilTypeService.create(oilType));
    }
  }

  private createFromForm(): IOilType {
    const entity = {
      ...new OilType(),
      id: this.editForm.get(['id']).value,
      arName: this.editForm.get(['arName']).value,
      enName: this.editForm.get(['enName']).value,
      vehicleDetails: this.editForm.get(['vehicleDetails']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOilType>>) {
    result.subscribe((res: HttpResponse<IOilType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
