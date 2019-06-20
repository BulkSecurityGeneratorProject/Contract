import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IIdType, IdType } from 'app/shared/model/id-type.model';
import { IdTypeService } from './id-type.service';
import { IPersonInfo } from 'app/shared/model/person-info.model';
import { PersonInfoService } from 'app/entities/person-info';

@Component({
  selector: 'jhi-id-type-update',
  templateUrl: './id-type-update.component.html'
})
export class IdTypeUpdateComponent implements OnInit {
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
    protected idTypeService: IdTypeService,
    protected personInfoService: PersonInfoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ idType }) => {
      this.updateForm(idType);
    });
    this.personInfoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPersonInfo[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPersonInfo[]>) => response.body)
      )
      .subscribe((res: IPersonInfo[]) => (this.personinfos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(idType: IIdType) {
    this.editForm.patchValue({
      id: idType.id,
      arName: idType.arName,
      enName: idType.enName,
      personInfo: idType.personInfo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const idType = this.createFromForm();
    if (idType.id !== undefined) {
      this.subscribeToSaveResponse(this.idTypeService.update(idType));
    } else {
      this.subscribeToSaveResponse(this.idTypeService.create(idType));
    }
  }

  private createFromForm(): IIdType {
    const entity = {
      ...new IdType(),
      id: this.editForm.get(['id']).value,
      arName: this.editForm.get(['arName']).value,
      enName: this.editForm.get(['enName']).value,
      personInfo: this.editForm.get(['personInfo']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIdType>>) {
    result.subscribe((res: HttpResponse<IIdType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
