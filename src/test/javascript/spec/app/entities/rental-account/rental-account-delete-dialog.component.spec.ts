/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ContractTestModule } from '../../../test.module';
import { RentalAccountDeleteDialogComponent } from 'app/entities/rental-account/rental-account-delete-dialog.component';
import { RentalAccountService } from 'app/entities/rental-account/rental-account.service';

describe('Component Tests', () => {
  describe('RentalAccount Management Delete Component', () => {
    let comp: RentalAccountDeleteDialogComponent;
    let fixture: ComponentFixture<RentalAccountDeleteDialogComponent>;
    let service: RentalAccountService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ContractTestModule],
        declarations: [RentalAccountDeleteDialogComponent]
      })
        .overrideTemplate(RentalAccountDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RentalAccountDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RentalAccountService);
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
