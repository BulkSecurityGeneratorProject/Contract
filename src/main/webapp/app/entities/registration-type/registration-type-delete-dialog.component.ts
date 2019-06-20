import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRegistrationType } from 'app/shared/model/registration-type.model';
import { RegistrationTypeService } from './registration-type.service';

@Component({
  selector: 'jhi-registration-type-delete-dialog',
  templateUrl: './registration-type-delete-dialog.component.html'
})
export class RegistrationTypeDeleteDialogComponent {
  registrationType: IRegistrationType;

  constructor(
    protected registrationTypeService: RegistrationTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.registrationTypeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'registrationTypeListModification',
        content: 'Deleted an registrationType'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-registration-type-delete-popup',
  template: ''
})
export class RegistrationTypeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ registrationType }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(RegistrationTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.registrationType = registrationType;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/registration-type', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/registration-type', { outlets: { popup: null } }]);
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
