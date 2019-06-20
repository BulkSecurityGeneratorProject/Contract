import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INationality } from 'app/shared/model/nationality.model';
import { NationalityService } from './nationality.service';

@Component({
  selector: 'jhi-nationality-delete-dialog',
  templateUrl: './nationality-delete-dialog.component.html'
})
export class NationalityDeleteDialogComponent {
  nationality: INationality;

  constructor(
    protected nationalityService: NationalityService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.nationalityService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'nationalityListModification',
        content: 'Deleted an nationality'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-nationality-delete-popup',
  template: ''
})
export class NationalityDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ nationality }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(NationalityDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.nationality = nationality;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/nationality', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/nationality', { outlets: { popup: null } }]);
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
