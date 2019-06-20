import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContractStatus } from 'app/shared/model/contract-status.model';
import { ContractStatusService } from './contract-status.service';

@Component({
  selector: 'jhi-contract-status-delete-dialog',
  templateUrl: './contract-status-delete-dialog.component.html'
})
export class ContractStatusDeleteDialogComponent {
  contractStatus: IContractStatus;

  constructor(
    protected contractStatusService: ContractStatusService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.contractStatusService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'contractStatusListModification',
        content: 'Deleted an contractStatus'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-contract-status-delete-popup',
  template: ''
})
export class ContractStatusDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ contractStatus }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ContractStatusDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.contractStatus = contractStatus;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/contract-status', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/contract-status', { outlets: { popup: null } }]);
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
