/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ContractTestModule } from '../../../test.module';
import { ContractTypeDeleteDialogComponent } from 'app/entities/contract-type/contract-type-delete-dialog.component';
import { ContractTypeService } from 'app/entities/contract-type/contract-type.service';

describe('Component Tests', () => {
  describe('ContractType Management Delete Component', () => {
    let comp: ContractTypeDeleteDialogComponent;
    let fixture: ComponentFixture<ContractTypeDeleteDialogComponent>;
    let service: ContractTypeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ContractTestModule],
        declarations: [ContractTypeDeleteDialogComponent]
      })
        .overrideTemplate(ContractTypeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContractTypeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContractTypeService);
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
