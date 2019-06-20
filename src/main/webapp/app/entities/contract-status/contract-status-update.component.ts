import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IContractStatus, ContractStatus } from 'app/shared/model/contract-status.model';
import { ContractStatusService } from './contract-status.service';
import { IRentContract } from 'app/shared/model/rent-contract.model';
import { RentContractService } from 'app/entities/rent-contract';

@Component({
  selector: 'jhi-contract-status-update',
  templateUrl: './contract-status-update.component.html'
})
export class ContractStatusUpdateComponent implements OnInit {
  isSaving: boolean;

  rentcontracts: IRentContract[];

  editForm = this.fb.group({
    id: [],
    arName: [],
    enName: [],
    rentContract: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected contractStatusService: ContractStatusService,
    protected rentContractService: RentContractService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ contractStatus }) => {
      this.updateForm(contractStatus);
    });
    this.rentContractService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IRentContract[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRentContract[]>) => response.body)
      )
      .subscribe((res: IRentContract[]) => (this.rentcontracts = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(contractStatus: IContractStatus) {
    this.editForm.patchValue({
      id: contractStatus.id,
      arName: contractStatus.arName,
      enName: contractStatus.enName,
      rentContract: contractStatus.rentContract
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const contractStatus = this.createFromForm();
    if (contractStatus.id !== undefined) {
      this.subscribeToSaveResponse(this.contractStatusService.update(contractStatus));
    } else {
      this.subscribeToSaveResponse(this.contractStatusService.create(contractStatus));
    }
  }

  private createFromForm(): IContractStatus {
    const entity = {
      ...new ContractStatus(),
      id: this.editForm.get(['id']).value,
      arName: this.editForm.get(['arName']).value,
      enName: this.editForm.get(['enName']).value,
      rentContract: this.editForm.get(['rentContract']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContractStatus>>) {
    result.subscribe((res: HttpResponse<IContractStatus>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackRentContractById(index: number, item: IRentContract) {
    return item.id;
  }
}
