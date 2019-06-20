/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ContractTestModule } from '../../../test.module';
import { VehicleItemStatusDeleteDialogComponent } from 'app/entities/vehicle-item-status/vehicle-item-status-delete-dialog.component';
import { VehicleItemStatusService } from 'app/entities/vehicle-item-status/vehicle-item-status.service';

describe('Component Tests', () => {
  describe('VehicleItemStatus Management Delete Component', () => {
    let comp: VehicleItemStatusDeleteDialogComponent;
    let fixture: ComponentFixture<VehicleItemStatusDeleteDialogComponent>;
    let service: VehicleItemStatusService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ContractTestModule],
        declarations: [VehicleItemStatusDeleteDialogComponent]
      })
        .overrideTemplate(VehicleItemStatusDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VehicleItemStatusDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VehicleItemStatusService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
