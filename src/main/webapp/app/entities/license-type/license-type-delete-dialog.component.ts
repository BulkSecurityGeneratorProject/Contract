import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILicenseType } from 'app/shared/model/license-type.model';
import { LicenseTypeService } from './license-type.service';

@Component({
  selector: 'jhi-license-type-delete-dialog',
  templateUrl: './license-type-delete-dialog.component.html'
})
export class LicenseTypeDeleteDialogComponent {
  licenseType: ILicenseType;

  constructor(
    protected licenseTypeService: LicenseTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.licenseTypeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'licenseTypeListModification',
        content: 'Deleted an licenseType'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-license-type-delete-popup',
  template: ''
})
export class LicenseTypeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ licenseType }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(LicenseTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.licenseType = licenseType;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/license-type', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/license-type', { outlets: { popup: null } }]);
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
