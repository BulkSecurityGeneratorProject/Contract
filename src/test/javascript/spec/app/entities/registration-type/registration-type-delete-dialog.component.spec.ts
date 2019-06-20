/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ContractTestModule } from '../../../test.module';
import { RegistrationTypeDeleteDialogComponent } from 'app/entities/registration-type/registration-type-delete-dialog.component';
import { RegistrationTypeService } from 'app/entities/registration-type/registration-type.service';

describe('Component Tests', () => {
  describe('RegistrationType Management Delete Component', () => {
    let comp: RegistrationTypeDeleteDialogComponent;
    let fixture: ComponentFixture<RegistrationTypeDeleteDialogComponent>;
    let service: RegistrationTypeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ContractTestModule],
        declarations: [RegistrationTypeDeleteDialogComponent]
      })
        .overrideTemplate(RegistrationTypeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RegistrationTypeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RegistrationTypeService);
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
