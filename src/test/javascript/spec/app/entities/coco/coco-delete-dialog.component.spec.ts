/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ContractTestModule } from '../../../test.module';
import { COCODeleteDialogComponent } from 'app/entities/coco/coco-delete-dialog.component';
import { COCOService } from 'app/entities/coco/coco.service';

describe('Component Tests', () => {
  describe('COCO Management Delete Component', () => {
    let comp: COCODeleteDialogComponent;
    let fixture: ComponentFixture<COCODeleteDialogComponent>;
    let service: COCOService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ContractTestModule],
        declarations: [COCODeleteDialogComponent]
      })
        .overrideTemplate(COCODeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(COCODeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(COCOService);
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
