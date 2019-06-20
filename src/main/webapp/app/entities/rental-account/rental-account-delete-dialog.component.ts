import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRentalAccount } from 'app/shared/model/rental-account.model';
import { RentalAccountService } from './rental-account.service';

@Component({
  selector: 'jhi-rental-account-delete-dialog',
  templateUrl: './rental-account-delete-dialog.component.html'
})
export class RentalAccountDeleteDialogComponent {
  rentalAccount: IRentalAccount;

  constructor(
    protected rentalAccountService: RentalAccountService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.rentalAccountService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'rentalAccountListModification',
        content: 'Deleted an rentalAccount'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-rental-account-delete-popup',
  template: ''
})
export class RentalAccountDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ rentalAccount }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(RentalAccountDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.rentalAccount = rentalAccount;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/rental-account', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/rental-account', { outlets: { popup: null } }]);
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
