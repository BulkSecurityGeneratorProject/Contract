import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ICOCO, COCO } from 'app/shared/model/coco.model';
import { COCOService } from './coco.service';

@Component({
  selector: 'jhi-coco-update',
  templateUrl: './coco-update.component.html'
})
export class COCOUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    coco: []
  });

  constructor(protected cOCOService: COCOService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ cOCO }) => {
      this.updateForm(cOCO);
    });
  }

  updateForm(cOCO: ICOCO) {
    this.editForm.patchValue({
      id: cOCO.id,
      coco: cOCO.coco
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const cOCO = this.createFromForm();
    if (cOCO.id !== undefined) {
      this.subscribeToSaveResponse(this.cOCOService.update(cOCO));
    } else {
      this.subscribeToSaveResponse(this.cOCOService.create(cOCO));
    }
  }

  private createFromForm(): ICOCO {
    const entity = {
      ...new COCO(),
      id: this.editForm.get(['id']).value,
      coco: this.editForm.get(['coco']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICOCO>>) {
    result.subscribe((res: HttpResponse<ICOCO>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
