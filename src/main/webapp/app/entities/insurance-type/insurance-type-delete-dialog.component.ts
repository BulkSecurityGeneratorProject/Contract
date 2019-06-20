import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInsuranceType } from 'app/shared/model/insurance-type.model';
import { InsuranceTypeService } from './insurance-type.service';

@Component({
  selector: 'jhi-insurance-type-delete-dialog',
  templateUrl: './insurance-type-delete-dialog.component.html'
})
export class InsuranceTypeDeleteDialogComponent {
  insuranceType: IInsuranceType;

  constructor(
    protected insuranceTypeService: InsuranceTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.insuranceTypeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'insuranceTypeListModification',
        content: 'Deleted an insuranceType'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-insurance-type-delete-popup',
  template: ''
})
export class InsuranceTypeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ insuranceType }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(InsuranceTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.insuranceType = insuranceType;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/insurance-type', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/insurance-type', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
