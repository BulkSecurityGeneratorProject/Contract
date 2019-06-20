import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IPersonInfo, PersonInfo } from 'app/shared/model/person-info.model';
import { PersonInfoService } from './person-info.service';

@Component({
  selector: 'jhi-person-info-update',
  templateUrl: './person-info-update.component.html'
})
export class PersonInfoUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    arFullName: [null, [Validators.required]],
    enFullName: [null, [Validators.required]],
    birthDate: [null, [Validators.required]],
    hijriBirthDate: [null, [Validators.required]],
    idExpiryDate: [],
    hijriIdExpiryDate: [],
    idCopyNumber: [],
    issuePlace: [],
    mobile: [null, [Validators.required]],
    licenseExpiryDate: [],
    address: [],
    nationalAddress: [],
    workAddress: []
  });

  constructor(protected personInfoService: PersonInfoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ personInfo }) => {
      this.updateForm(personInfo);
    });
  }

  updateForm(personInfo: IPersonInfo) {
    this.editForm.patchValue({
      id: personInfo.id,
      arFullName: personInfo.arFullName,
      enFullName: personInfo.enFullName,
      birthDate: personInfo.birthDate != null ? personInfo.birthDate.format(DATE_TIME_FORMAT) : null,
      hijriBirthDate: personInfo.hijriBirthDate,
      idExpiryDate: personInfo.idExpiryDate != null ? personInfo.idExpiryDate.format(DATE_TIME_FORMAT) : null,
      hijriIdExpiryDate: personInfo.hijriIdExpiryDate,
      idCopyNumber: personInfo.idCopyNumber,
      issuePlace: personInfo.issuePlace,
      mobile: personInfo.mobile,
      licenseExpiryDate: personInfo.licenseExpiryDate != null ? personInfo.licenseExpiryDate.format(DATE_TIME_FORMAT) : null,
      address: personInfo.address,
      nationalAddress: personInfo.nationalAddress,
      workAddress: personInfo.workAddress
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const personInfo = this.createFromForm();
    if (personInfo.id !== undefined) {
      this.subscribeToSaveResponse(this.personInfoService.update(personInfo));
    } else {
      this.subscribeToSaveResponse(this.personInfoService.create(personInfo));
    }
  }

  private createFromForm(): IPersonInfo {
    const entity = {
      ...new PersonInfo(),
      id: this.editForm.get(['id']).value,
      arFullName: this.editForm.get(['arFullName']).value,
      enFullName: this.editForm.get(['enFullName']).value,
      birthDate:
        this.editForm.get(['birthDate']).value != null ? moment(this.editForm.get(['birthDate']).value, DATE_TIME_FORMAT) : undefined,
      hijriBirthDate: this.editForm.get(['hijriBirthDate']).value,
      idExpiryDate:
        this.editForm.get(['idExpiryDate']).value != null ? moment(this.editForm.get(['idExpiryDate']).value, DATE_TIME_FORMAT) : undefined,
      hijriIdExpiryDate: this.editForm.get(['hijriIdExpiryDate']).value,
      idCopyNumber: this.editForm.get(['idCopyNumber']).value,
      issuePlace: this.editForm.get(['issuePlace']).value,
      mobile: this.editForm.get(['mobile']).value,
      licenseExpiryDate:
        this.editForm.get(['licenseExpiryDate']).value != null
          ? moment(this.editForm.get(['licenseExpiryDate']).value, DATE_TIME_FORMAT)
          : undefined,
      address: this.editForm.get(['address']).value,
      nationalAddress: this.editForm.get(['nationalAddress']).value,
      workAddress: this.editForm.get(['workAddress']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersonInfo>>) {
    result.subscribe((res: HttpResponse<IPersonInfo>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
