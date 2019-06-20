import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IRentContract, RentContract } from 'app/shared/model/rent-contract.model';
import { RentContractService } from './rent-contract.service';
import { IVehicleItemStatus } from 'app/shared/model/vehicle-item-status.model';
import { VehicleItemStatusService } from 'app/entities/vehicle-item-status';
import { IPersonInfo } from 'app/shared/model/person-info.model';
import { PersonInfoService } from 'app/entities/person-info';
import { IVehicleDetails } from 'app/shared/model/vehicle-details.model';
import { VehicleDetailsService } from 'app/entities/vehicle-details';
import { IPaymentDetails } from 'app/shared/model/payment-details.model';
import { PaymentDetailsService } from 'app/entities/payment-details';
import { IRentalAccount } from 'app/shared/model/rental-account.model';
import { RentalAccountService } from 'app/entities/rental-account';
import { IBranch } from 'app/shared/model/branch.model';
import { BranchService } from 'app/entities/branch';

@Component({
  selector: 'jhi-rent-contract-update',
  templateUrl: './rent-contract-update.component.html'
})
export class RentContractUpdateComponent implements OnInit {
  isSaving: boolean;

  rentstatuses: IVehicleItemStatus[];

  returnstatuses: IVehicleItemStatus[];

  renters: IPersonInfo[];

  extradrivers: IPersonInfo[];

  renteddrivers: IPersonInfo[];

  vehicledetails: IVehicleDetails[];

  paymentdetails: IPaymentDetails[];

  rentalaccounts: IRentalAccount[];

  branches: IBranch[];

  editForm = this.fb.group({
    id: [],
    contractNumber: [null, [Validators.required]],
    contractSignDate: [null, [Validators.required]],
    contractStartDate: [null, [Validators.required]],
    contractEndDate: [null, [Validators.required]],
    contractSignLocation: [null, [Validators.required]],
    extendedTo: [null, [Validators.required]],
    extendedTimes: [null, [Validators.required]],
    rentDuration: [null, [Validators.required]],
    rentDayCost: [null, [Validators.required]],
    rentHourCost: [null, [Validators.required]],
    odometerKmBefore: [null, [Validators.required]],
    allowedLateHours: [null, [Validators.required]],
    lateHourCost: [null, [Validators.required]],
    allowedKmPerDay: [null, [Validators.required]],
    allowedKmPerHour: [null, [Validators.required]],
    carTransferCost: [null, [Validators.required]],
    receiveLocation: [null, [Validators.required]],
    returnLocation: [null, [Validators.required]],
    earlyReturnPolicy: [null, [Validators.required]],
    contractExtensionMechanism: [null, [Validators.required]],
    accidentsAndFaultReportingMechanism: [null, [Validators.required]],
    odometerKmAfter: [null, [Validators.required]],
    lateHours: [null, [Validators.required]],
    oilChangeTime: [null, [Validators.required]],
    rentStatus: [],
    returnStatus: [],
    renter: [],
    extraDriver: [],
    rentedDriver: [],
    vehicleDetails: [],
    paymentDetails: [],
    account: [],
    branch: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected rentContractService: RentContractService,
    protected vehicleItemStatusService: VehicleItemStatusService,
    protected personInfoService: PersonInfoService,
    protected vehicleDetailsService: VehicleDetailsService,
    protected paymentDetailsService: PaymentDetailsService,
    protected rentalAccountService: RentalAccountService,
    protected branchService: BranchService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ rentContract }) => {
      this.updateForm(rentContract);
    });
    this.vehicleItemStatusService
      .query({ filter: 'rentcontract-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IVehicleItemStatus[]>) => mayBeOk.ok),
        map((response: HttpResponse<IVehicleItemStatus[]>) => response.body)
      )
      .subscribe(
        (res: IVehicleItemStatus[]) => {
          if (!this.editForm.get('rentStatus').value || !this.editForm.get('rentStatus').value.id) {
            this.rentstatuses = res;
          } else {
            this.vehicleItemStatusService
              .find(this.editForm.get('rentStatus').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IVehicleItemStatus>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IVehicleItemStatus>) => subResponse.body)
              )
              .subscribe(
                (subRes: IVehicleItemStatus) => (this.rentstatuses = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.vehicleItemStatusService
      .query({ filter: 'rentcontract-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IVehicleItemStatus[]>) => mayBeOk.ok),
        map((response: HttpResponse<IVehicleItemStatus[]>) => response.body)
      )
      .subscribe(
        (res: IVehicleItemStatus[]) => {
          if (!this.editForm.get('returnStatus').value || !this.editForm.get('returnStatus').value.id) {
            this.returnstatuses = res;
          } else {
            this.vehicleItemStatusService
              .find(this.editForm.get('returnStatus').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IVehicleItemStatus>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IVehicleItemStatus>) => subResponse.body)
              )
              .subscribe(
                (subRes: IVehicleItemStatus) => (this.returnstatuses = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.personInfoService
      .query({ filter: 'rentcontract-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IPersonInfo[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPersonInfo[]>) => response.body)
      )
      .subscribe(
        (res: IPersonInfo[]) => {
          if (!this.editForm.get('renter').value || !this.editForm.get('renter').value.id) {
            this.renters = res;
          } else {
            this.personInfoService
              .find(this.editForm.get('renter').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IPersonInfo>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IPersonInfo>) => subResponse.body)
              )
              .subscribe(
                (subRes: IPersonInfo) => (this.renters = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.personInfoService
      .query({ filter: 'rentcontract-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IPersonInfo[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPersonInfo[]>) => response.body)
      )
      .subscribe(
        (res: IPersonInfo[]) => {
          if (!this.editForm.get('extraDriver').value || !this.editForm.get('extraDriver').value.id) {
            this.extradrivers = res;
          } else {
            this.personInfoService
              .find(this.editForm.get('extraDriver').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IPersonInfo>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IPersonInfo>) => subResponse.body)
              )
              .subscribe(
                (subRes: IPersonInfo) => (this.extradrivers = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.personInfoService
      .query({ filter: 'rentcontract-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IPersonInfo[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPersonInfo[]>) => response.body)
      )
      .subscribe(
        (res: IPersonInfo[]) => {
          if (!this.editForm.get('rentedDriver').value || !this.editForm.get('rentedDriver').value.id) {
            this.renteddrivers = res;
          } else {
            this.personInfoService
              .find(this.editForm.get('rentedDriver').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IPersonInfo>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IPersonInfo>) => subResponse.body)
              )
              .subscribe(
                (subRes: IPersonInfo) => (this.renteddrivers = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.vehicleDetailsService
      .query({ filter: 'rentcontract-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IVehicleDetails[]>) => mayBeOk.ok),
        map((response: HttpResponse<IVehicleDetails[]>) => response.body)
      )
      .subscribe(
        (res: IVehicleDetails[]) => {
          if (!this.editForm.get('vehicleDetails').value || !this.editForm.get('vehicleDetails').value.id) {
            this.vehicledetails = res;
          } else {
            this.vehicleDetailsService
              .find(this.editForm.get('vehicleDetails').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IVehicleDetails>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IVehicleDetails>) => subResponse.body)
              )
              .subscribe(
                (subRes: IVehicleDetails) => (this.vehicledetails = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.paymentDetailsService
      .query({ filter: 'rentcontract-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IPaymentDetails[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPaymentDetails[]>) => response.body)
      )
      .subscribe(
        (res: IPaymentDetails[]) => {
          if (!this.editForm.get('paymentDetails').value || !this.editForm.get('paymentDetails').value.id) {
            this.paymentdetails = res;
          } else {
            this.paymentDetailsService
              .find(this.editForm.get('paymentDetails').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IPaymentDetails>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IPaymentDetails>) => subResponse.body)
              )
              .subscribe(
                (subRes: IPaymentDetails) => (this.paymentdetails = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.rentalAccountService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IRentalAccount[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRentalAccount[]>) => response.body)
      )
      .subscribe((res: IRentalAccount[]) => (this.rentalaccounts = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.branchService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IBranch[]>) => mayBeOk.ok),
        map((response: HttpResponse<IBranch[]>) => response.body)
      )
      .subscribe((res: IBranch[]) => (this.branches = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(rentContract: IRentContract) {
    this.editForm.patchValue({
      id: rentContract.id,
      contractNumber: rentContract.contractNumber,
      contractSignDate: rentContract.contractSignDate != null ? rentContract.contractSignDate.format(DATE_TIME_FORMAT) : null,
      contractStartDate: rentContract.contractStartDate != null ? rentContract.contractStartDate.format(DATE_TIME_FORMAT) : null,
      contractEndDate: rentContract.contractEndDate != null ? rentContract.contractEndDate.format(DATE_TIME_FORMAT) : null,
      contractSignLocation: rentContract.contractSignLocation,
      extendedTo: rentContract.extendedTo,
      extendedTimes: rentContract.extendedTimes,
      rentDuration: rentContract.rentDuration,
      rentDayCost: rentContract.rentDayCost,
      rentHourCost: rentContract.rentHourCost,
      odometerKmBefore: rentContract.odometerKmBefore,
      allowedLateHours: rentContract.allowedLateHours,
      lateHourCost: rentContract.lateHourCost,
      allowedKmPerDay: rentContract.allowedKmPerDay,
      allowedKmPerHour: rentContract.allowedKmPerHour,
      carTransferCost: rentContract.carTransferCost,
      receiveLocation: rentContract.receiveLocation,
      returnLocation: rentContract.returnLocation,
      earlyReturnPolicy: rentContract.earlyReturnPolicy,
      contractExtensionMechanism: rentContract.contractExtensionMechanism,
      accidentsAndFaultReportingMechanism: rentContract.accidentsAndFaultReportingMechanism,
      odometerKmAfter: rentContract.odometerKmAfter,
      lateHours: rentContract.lateHours,
      oilChangeTime: rentContract.oilChangeTime != null ? rentContract.oilChangeTime.format(DATE_TIME_FORMAT) : null,
      rentStatus: rentContract.rentStatus,
      returnStatus: rentContract.returnStatus,
      renter: rentContract.renter,
      extraDriver: rentContract.extraDriver,
      rentedDriver: rentContract.rentedDriver,
      vehicleDetails: rentContract.vehicleDetails,
      paymentDetails: rentContract.paymentDetails,
      account: rentContract.account,
      branch: rentContract.branch
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const rentContract = this.createFromForm();
    if (rentContract.id !== undefined) {
      this.subscribeToSaveResponse(this.rentContractService.update(rentContract));
    } else {
      this.subscribeToSaveResponse(this.rentContractService.create(rentContract));
    }
  }

  private createFromForm(): IRentContract {
    const entity = {
      ...new RentContract(),
      id: this.editForm.get(['id']).value,
      contractNumber: this.editForm.get(['contractNumber']).value,
      contractSignDate:
        this.editForm.get(['contractSignDate']).value != null
          ? moment(this.editForm.get(['contractSignDate']).value, DATE_TIME_FORMAT)
          : undefined,
      contractStartDate:
        this.editForm.get(['contractStartDate']).value != null
          ? moment(this.editForm.get(['contractStartDate']).value, DATE_TIME_FORMAT)
          : undefined,
      contractEndDate:
        this.editForm.get(['contractEndDate']).value != null
          ? moment(this.editForm.get(['contractEndDate']).value, DATE_TIME_FORMAT)
          : undefined,
      contractSignLocation: this.editForm.get(['contractSignLocation']).value,
      extendedTo: this.editForm.get(['extendedTo']).value,
      extendedTimes: this.editForm.get(['extendedTimes']).value,
      rentDuration: this.editForm.get(['rentDuration']).value,
      rentDayCost: this.editForm.get(['rentDayCost']).value,
      rentHourCost: this.editForm.get(['rentHourCost']).value,
      odometerKmBefore: this.editForm.get(['odometerKmBefore']).value,
      allowedLateHours: this.editForm.get(['allowedLateHours']).value,
      lateHourCost: this.editForm.get(['lateHourCost']).value,
      allowedKmPerDay: this.editForm.get(['allowedKmPerDay']).value,
      allowedKmPerHour: this.editForm.get(['allowedKmPerHour']).value,
      carTransferCost: this.editForm.get(['carTransferCost']).value,
      receiveLocation: this.editForm.get(['receiveLocation']).value,
      returnLocation: this.editForm.get(['returnLocation']).value,
      earlyReturnPolicy: this.editForm.get(['earlyReturnPolicy']).value,
      contractExtensionMechanism: this.editForm.get(['contractExtensionMechanism']).value,
      accidentsAndFaultReportingMechanism: this.editForm.get(['accidentsAndFaultReportingMechanism']).value,
      odometerKmAfter: this.editForm.get(['odometerKmAfter']).value,
      lateHours: this.editForm.get(['lateHours']).value,
      oilChangeTime:
        this.editForm.get(['oilChangeTime']).value != null
          ? moment(this.editForm.get(['oilChangeTime']).value, DATE_TIME_FORMAT)
          : undefined,
      rentStatus: this.editForm.get(['rentStatus']).value,
      returnStatus: this.editForm.get(['returnStatus']).value,
      renter: this.editForm.get(['renter']).value,
      extraDriver: this.editForm.get(['extraDriver']).value,
      rentedDriver: this.editForm.get(['rentedDriver']).value,
      vehicleDetails: this.editForm.get(['vehicleDetails']).value,
      paymentDetails: this.editForm.get(['paymentDetails']).value,
      account: this.editForm.get(['account']).value,
      branch: this.editForm.get(['branch']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRentContract>>) {
    result.subscribe((res: HttpResponse<IRentContract>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackVehicleItemStatusById(index: number, item: IVehicleItemStatus) {
    return item.id;
  }

  trackPersonInfoById(index: number, item: IPersonInfo) {
    return item.id;
  }

  trackVehicleDetailsById(index: number, item: IVehicleDetails) {
    return item.id;
  }

  trackPaymentDetailsById(index: number, item: IPaymentDetails) {
    return item.id;
  }

  trackRentalAccountById(index: number, item: IRentalAccount) {
    return item.id;
  }

  trackBranchById(index: number, item: IBranch) {
    return item.id;
  }
}
