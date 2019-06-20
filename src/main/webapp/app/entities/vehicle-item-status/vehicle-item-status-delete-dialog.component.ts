import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVehicleItemStatus } from 'app/shared/model/vehicle-item-status.model';
import { VehicleItemStatusService } from './vehicle-item-status.service';

@Component({
  selector: 'jhi-vehicle-item-status-delete-dialog',
  templateUrl: './vehicle-item-status-delete-dialog.component.html'
})
export class VehicleItemStatusDeleteDialogComponent {
  vehicleItemStatus: IVehicleItemStatus;

  constructor(
    protected vehicleItemStatusService: VehicleItemStatusService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.vehicleItemStatusService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'vehicleItemStatusListModification',
        content: 'Deleted an vehicleItemStatus'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-vehicle-item-status-delete-popup',
  template: ''
})
export class VehicleItemStatusDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ vehicleItemStatus }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(VehicleItemStatusDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.vehicleItemStatus = vehicleItemStatus;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/vehicle-item-status', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/vehicle-item-status', { outlets: { popup: null } }]);
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
