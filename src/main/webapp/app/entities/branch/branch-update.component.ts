import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IBranch, Branch } from 'app/shared/model/branch.model';
import { BranchService } from './branch.service';

@Component({
  selector: 'jhi-branch-update',
  templateUrl: './branch-update.component.html'
})
export class BranchUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    arName: [],
    enName: []
  });

  constructor(protected branchService: BranchService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ branch }) => {
      this.updateForm(branch);
    });
  }

  updateForm(branch: IBranch) {
    this.editForm.patchValue({
      id: branch.id,
      arName: branch.arName,
      enName: branch.enName
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const branch = this.createFromForm();
    if (branch.id !== undefined) {
      this.subscribeToSaveResponse(this.branchService.update(branch));
    } else {
      this.subscribeToSaveResponse(this.branchService.create(branch));
    }
  }

  private createFromForm(): IBranch {
    const entity = {
      ...new Branch(),
      id: this.editForm.get(['id']).value,
      arName: this.editForm.get(['arName']).value,
      enName: this.editForm.get(['enName']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBranch>>) {
    result.subscribe((res: HttpResponse<IBranch>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
