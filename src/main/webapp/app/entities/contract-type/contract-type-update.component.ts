import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IContractType, ContractType } from 'app/shared/model/contract-type.model';
import { ContractTypeService } from './contract-type.service';
import { IRentContract } from 'app/shared/model/rent-contract.model';
import { RentContractService } from 'app/entities/rent-contract';

@Component({
  selector: 'jhi-contract-type-update',
  templateUrl: './contract-type-update.component.html'
})
export class ContractTypeUpdateComponent implements OnInit {
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
    protected contractTypeService: ContractTypeService,
    protected rentContractService: RentContractService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ contractType }) => {
      this.updateForm(contractType);
    });
    this.rentContractService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IRentContract[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRentContract[]>) => response.body)
      )
      .subscribe((res: IRentContract[]) => (this.rentcontracts = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(contractType: IContractType) {
    this.editForm.patchValue({
      id: contractType.id,
      arName: contractType.arName,
      enName: contractType.enName,
      rentContract: contractType.rentContract
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const contractType = this.createFromForm();
    if (contractType.id !== undefined) {
      this.subscribeToSaveResponse(this.contractTypeService.update(contractType));
    } else {
      this.subscribeToSaveResponse(this.contractTypeService.create(contractType));
    }
  }

  private createFromForm(): IContractType {
    const entity = {
      ...new ContractType(),
      id: this.editForm.get(['id']).value,
      arName: this.editForm.get(['arName']).value,
      enName: this.editForm.get(['enName']).value,
      rentContract: this.editForm.get(['rentContract']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContractType>>) {
    result.subscribe((res: HttpResponse<IContractType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
