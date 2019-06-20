import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContractType } from 'app/shared/model/contract-type.model';
import { ContractTypeService } from './contract-type.service';

@Component({
  selector: 'jhi-contract-type-delete-dialog',
  templateUrl: './contract-type-delete-dialog.component.html'
})
export class ContractTypeDeleteDialogComponent {
  contractType: IContractType;

  constructor(
    protected contractTypeService: ContractTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.contractTypeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'contractTypeListModification',
        content: 'Deleted an contractType'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-contract-type-delete-popup',
  template: ''
})
export class ContractTypeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ contractType }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ContractTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.contractType = contractType;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/contract-type', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/contract-type', { outlets: { popup: null } }]);
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
