import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDriveArea } from 'app/shared/model/drive-area.model';
import { DriveAreaService } from './drive-area.service';

@Component({
  selector: 'jhi-drive-area-delete-dialog',
  templateUrl: './drive-area-delete-dialog.component.html'
})
export class DriveAreaDeleteDialogComponent {
  driveArea: IDriveArea;

  constructor(protected driveAreaService: DriveAreaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.driveAreaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'driveAreaListModification',
        content: 'Deleted an driveArea'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-drive-area-delete-popup',
  template: ''
})
export class DriveAreaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ driveArea }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DriveAreaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.driveArea = driveArea;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/drive-area', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/drive-area', { outlets: { popup: null } }]);
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
