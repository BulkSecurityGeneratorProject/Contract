import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIdType } from 'app/shared/model/id-type.model';
import { IdTypeService } from './id-type.service';

@Component({
  selector: 'jhi-id-type-delete-dialog',
  templateUrl: './id-type-delete-dialog.component.html'
})
export class IdTypeDeleteDialogComponent {
  idType: IIdType;

  constructor(protected idTypeService: IdTypeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.idTypeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'idTypeListModification',
        content: 'Deleted an idType'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-id-type-delete-popup',
  template: ''
})
export class IdTypeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ idType }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(IdTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.idType = idType;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/id-type', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/id-type', { outlets: { popup: null } }]);
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
