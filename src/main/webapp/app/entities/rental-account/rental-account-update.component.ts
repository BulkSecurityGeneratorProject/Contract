import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IRentalAccount, RentalAccount } from 'app/shared/model/rental-account.model';
import { RentalAccountService } from './rental-account.service';

@Component({
  selector: 'jhi-rental-account-update',
  templateUrl: './rental-account-update.component.html'
})
export class RentalAccountUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    arName: [],
    enName: []
  });

  constructor(protected rentalAccountService: RentalAccountService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ rentalAccount }) => {
      this.updateForm(rentalAccount);
    });
  }

  updateForm(rentalAccount: IRentalAccount) {
    this.editForm.patchValue({
      id: rentalAccount.id,
      arName: rentalAccount.arName,
      enName: rentalAccount.enName
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const rentalAccount = this.createFromForm();
    if (rentalAccount.id !== undefined) {
      this.subscribeToSaveResponse(this.rentalAccountService.update(rentalAccount));
    } else {
      this.subscribeToSaveResponse(this.rentalAccountService.create(rentalAccount));
    }
  }

  private createFromForm(): IRentalAccount {
    const entity = {
      ...new RentalAccount(),
      id: this.editForm.get(['id']).value,
      arName: this.editForm.get(['arName']).value,
      enName: this.editForm.get(['enName']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRentalAccount>>) {
    result.subscribe((res: HttpResponse<IRentalAccount>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
