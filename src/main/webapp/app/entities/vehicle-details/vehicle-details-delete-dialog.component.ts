import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVehicleDetails } from 'app/shared/model/vehicle-details.model';
import { VehicleDetailsService } from './vehicle-details.service';

@Component({
  selector: 'jhi-vehicle-details-delete-dialog',
  templateUrl: './vehicle-details-delete-dialog.component.html'
})
export class VehicleDetailsDeleteDialogComponent {
  vehicleDetails: IVehicleDetails;

  constructor(
    protected vehicleDetailsService: VehicleDetailsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.vehicleDetailsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'vehicleDetailsListModification',
        content: 'Deleted an vehicleDetails'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-vehicle-details-delete-popup',
  template: ''
})
export class VehicleDetailsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ vehicleDetails }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(VehicleDetailsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.vehicleDetails = vehicleDetails;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/vehicle-details', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/vehicle-details', { outlets: { popup: null } }]);
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
