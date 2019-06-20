import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRentContract } from 'app/shared/model/rent-contract.model';
import { RentContractService } from './rent-contract.service';

@Component({
  selector: 'jhi-rent-contract-delete-dialog',
  templateUrl: './rent-contract-delete-dialog.component.html'
})
export class RentContractDeleteDialogComponent {
  rentContract: IRentContract;

  constructor(
    protected rentContractService: RentContractService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.rentContractService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'rentContractListModification',
        content: 'Deleted an rentContract'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-rent-contract-delete-popup',
  template: ''
})
export class RentContractDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ rentContract }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(RentContractDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.rentContract = rentContract;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/rent-contract', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/rent-contract', { outlets: { popup: null } }]);
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
