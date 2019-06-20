/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ContractTestModule } from '../../../test.module';
import { VehicleDetailsDeleteDialogComponent } from 'app/entities/vehicle-details/vehicle-details-delete-dialog.component';
import { VehicleDetailsService } from 'app/entities/vehicle-details/vehicle-details.service';

describe('Component Tests', () => {
  describe('VehicleDetails Management Delete Component', () => {
    let comp: VehicleDetailsDeleteDialogComponent;
    let fixture: ComponentFixture<VehicleDetailsDeleteDialogComponent>;
    let service: VehicleDetailsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ContractTestModule],
        declarations: [VehicleDetailsDeleteDialogComponent]
      })
        .overrideTemplate(VehicleDetailsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VehicleDetailsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VehicleDetailsService);
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
