import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDriveArea, DriveArea } from 'app/shared/model/drive-area.model';
import { DriveAreaService } from './drive-area.service';
import { IRentContract } from 'app/shared/model/rent-contract.model';
import { RentContractService } from 'app/entities/rent-contract';

@Component({
  selector: 'jhi-drive-area-update',
  templateUrl: './drive-area-update.component.html'
})
export class DriveAreaUpdateComponent implements OnInit {
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
    protected driveAreaService: DriveAreaService,
    protected rentContractService: RentContractService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ driveArea }) => {
      this.updateForm(driveArea);
    });
    this.rentContractService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IRentContract[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRentContract[]>) => response.body)
      )
      .subscribe((res: IRentContract[]) => (this.rentcontracts = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(driveArea: IDriveArea) {
    this.editForm.patchValue({
      id: driveArea.id,
      arName: driveArea.arName,
      enName: driveArea.enName,
      rentContract: driveArea.rentContract
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const driveArea = this.createFromForm();
    if (driveArea.id !== undefined) {
      this.subscribeToSaveResponse(this.driveAreaService.update(driveArea));
    } else {
      this.subscribeToSaveResponse(this.driveAreaService.create(driveArea));
    }
  }

  private createFromForm(): IDriveArea {
    const entity = {
      ...new DriveArea(),
      id: this.editForm.get(['id']).value,
      arName: this.editForm.get(['arName']).value,
      enName: this.editForm.get(['enName']).value,
      rentContract: this.editForm.get(['rentContract']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDriveArea>>) {
    result.subscribe((res: HttpResponse<IDriveArea>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
