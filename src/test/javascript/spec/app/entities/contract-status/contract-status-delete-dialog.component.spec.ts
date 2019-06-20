/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ContractTestModule } from '../../../test.module';
import { ContractStatusDeleteDialogComponent } from 'app/entities/contract-status/contract-status-delete-dialog.component';
import { ContractStatusService } from 'app/entities/contract-status/contract-status.service';

describe('Component Tests', () => {
  describe('ContractStatus Management Delete Component', () => {
    let comp: ContractStatusDeleteDialogComponent;
    let fixture: ComponentFixture<ContractStatusDeleteDialogComponent>;
    let service: ContractStatusService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ContractTestModule],
        declarations: [ContractStatusDeleteDialogComponent]
      })
        .overrideTemplate(ContractStatusDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContractStatusDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContractStatusService);
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
