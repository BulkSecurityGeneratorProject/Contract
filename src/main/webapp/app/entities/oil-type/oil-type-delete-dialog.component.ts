import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOilType } from 'app/shared/model/oil-type.model';
import { OilTypeService } from './oil-type.service';

@Component({
  selector: 'jhi-oil-type-delete-dialog',
  templateUrl: './oil-type-delete-dialog.component.html'
})
export class OilTypeDeleteDialogComponent {
  oilType: IOilType;

  constructor(protected oilTypeService: OilTypeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.oilTypeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'oilTypeListModification',
        content: 'Deleted an oilType'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-oil-type-delete-popup',
  template: ''
})
export class OilTypeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ oilType }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(OilTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.oilType = oilType;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/oil-type', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/oil-type', { outlets: { popup: null } }]);
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
