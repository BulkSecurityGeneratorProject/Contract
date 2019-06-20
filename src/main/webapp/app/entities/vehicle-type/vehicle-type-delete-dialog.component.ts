import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVehicleType } from 'app/shared/model/vehicle-type.model';
import { VehicleTypeService } from './vehicle-type.service';

@Component({
  selector: 'jhi-vehicle-type-delete-dialog',
  templateUrl: './vehicle-type-delete-dialog.component.html'
})
export class VehicleTypeDeleteDialogComponent {
  vehicleType: IVehicleType;

  constructor(
    protected vehicleTypeService: VehicleTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.vehicleTypeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'vehicleTypeListModification',
        content: 'Deleted an vehicleType'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-vehicle-type-delete-popup',
  template: ''
})
export class VehicleTypeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ vehicleType }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(VehicleTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.vehicleType = vehicleType;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/vehicle-type', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/vehicle-type', { outlets: { popup: null } }]);
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
