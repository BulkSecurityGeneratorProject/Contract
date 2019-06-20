import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ILicenseType, LicenseType } from 'app/shared/model/license-type.model';
import { LicenseTypeService } from './license-type.service';
import { IPersonInfo } from 'app/shared/model/person-info.model';
import { PersonInfoService } from 'app/entities/person-info';

@Component({
  selector: 'jhi-license-type-update',
  templateUrl: './license-type-update.component.html'
})
export class LicenseTypeUpdateComponent implements OnInit {
  isSaving: boolean;

  personinfos: IPersonInfo[];

  editForm = this.fb.group({
    id: [],
    arName: [],
    enName: [],
    personInfo: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected licenseTypeService: LicenseTypeService,
    protected personInfoService: PersonInfoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ licenseType }) => {
      this.updateForm(licenseType);
    });
    this.personInfoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPersonInfo[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPersonInfo[]>) => response.body)
      )
      .subscribe((res: IPersonInfo[]) => (this.personinfos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(licenseType: ILicenseType) {
    this.editForm.patchValue({
      id: licenseType.id,
      arName: licenseType.arName,
      enName: licenseType.enName,
      personInfo: licenseType.personInfo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const licenseType = this.createFromForm();
    if (licenseType.id !== undefined) {
      this.subscribeToSaveResponse(this.licenseTypeService.update(licenseType));
    } else {
      this.subscribeToSaveResponse(this.licenseTypeService.create(licenseType));
    }
  }

  private createFromForm(): ILicenseType {
    const entity = {
      ...new LicenseType(),
      id: this.editForm.get(['id']).value,
      arName: this.editForm.get(['arName']).value,
      enName: this.editForm.get(['enName']).value,
      personInfo: this.editForm.get(['personInfo']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILicenseType>>) {
    result.subscribe((res: HttpResponse<ILicenseType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackPersonInfoById(index: number, item: IPersonInfo) {
    return item.id;
  }
}
