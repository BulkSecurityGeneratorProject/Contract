/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { ContractTestModule } from '../../../test.module';
import { ContractStatusUpdateComponent } from 'app/entities/contract-status/contract-status-update.component';
import { ContractStatusService } from 'app/entities/contract-status/contract-status.service';
import { ContractStatus } from 'app/shared/model/contract-status.model';

describe('Component Tests', () => {
  describe('ContractStatus Management Update Component', () => {
    let comp: ContractStatusUpdateComponent;
    let fixture: ComponentFixture<ContractStatusUpdateComponent>;
    let service: ContractStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ContractTestModule],
        declarations: [ContractStatusUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ContractStatusUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContractStatusUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContractStatusService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ContractStatus(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ContractStatus();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
