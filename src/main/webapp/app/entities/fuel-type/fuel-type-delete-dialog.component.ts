import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFuelType } from 'app/shared/model/fuel-type.model';
import { FuelTypeService } from './fuel-type.service';

@Component({
  selector: 'jhi-fuel-type-delete-dialog',
  templateUrl: './fuel-type-delete-dialog.component.html'
})
export class FuelTypeDeleteDialogComponent {
  fuelType: IFuelType;

  constructor(protected fuelTypeService: FuelTypeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.fuelTypeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'fuelTypeListModification',
        content: 'Deleted an fuelType'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-fuel-type-delete-popup',
  template: ''
})
export class FuelTypeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ fuelType }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FuelTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.fuelType = fuelType;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/fuel-type', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/fuel-type', { outlets: { popup: null } }]);
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
