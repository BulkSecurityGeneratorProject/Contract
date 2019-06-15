import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICOCO } from 'app/shared/model/coco.model';
import { COCOService } from './coco.service';

@Component({
  selector: 'jhi-coco-delete-dialog',
  templateUrl: './coco-delete-dialog.component.html'
})
export class COCODeleteDialogComponent {
  cOCO: ICOCO;

  constructor(protected cOCOService: COCOService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.cOCOService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'cOCOListModification',
        content: 'Deleted an cOCO'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-coco-delete-popup',
  template: ''
})
export class COCODeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ cOCO }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(COCODeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.cOCO = cOCO;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/coco', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/coco', { outlets: { popup: null } }]);
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
