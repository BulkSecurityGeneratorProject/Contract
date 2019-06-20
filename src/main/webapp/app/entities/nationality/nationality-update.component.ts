import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { INationality, Nationality } from 'app/shared/model/nationality.model';
import { NationalityService } from './nationality.service';
import { IPersonInfo } from 'app/shared/model/person-info.model';
import { PersonInfoService } from 'app/entities/person-info';

@Component({
  selector: 'jhi-nationality-update',
  templateUrl: './nationality-update.component.html'
})
export class NationalityUpdateComponent implements OnInit {
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
    protected nationalityService: NationalityService,
    protected personInfoService: PersonInfoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ nationality }) => {
      this.updateForm(nationality);
    });
    this.personInfoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPersonInfo[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPersonInfo[]>) => response.body)
      )
      .subscribe((res: IPersonInfo[]) => (this.personinfos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(nationality: INationality) {
    this.editForm.patchValue({
      id: nationality.id,
      arName: nationality.arName,
      enName: nationality.enName,
      personInfo: nationality.personInfo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const nationality = this.createFromForm();
    if (nationality.id !== undefined) {
      this.subscribeToSaveResponse(this.nationalityService.update(nationality));
    } else {
      this.subscribeToSaveResponse(this.nationalityService.create(nationality));
    }
  }

  private createFromForm(): INationality {
    const entity = {
      ...new Nationality(),
      id: this.editForm.get(['id']).value,
      arName: this.editForm.get(['arName']).value,
      enName: this.editForm.get(['enName']).value,
      personInfo: this.editForm.get(['personInfo']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INationality>>) {
    result.subscribe((res: HttpResponse<INationality>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
