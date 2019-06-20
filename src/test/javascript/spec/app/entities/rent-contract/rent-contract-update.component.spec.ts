/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { ContractTestModule } from '../../../test.module';
import { RentContractUpdateComponent } from 'app/entities/rent-contract/rent-contract-update.component';
import { RentContractService } from 'app/entities/rent-contract/rent-contract.service';
import { RentContract } from 'app/shared/model/rent-contract.model';

describe('Component Tests', () => {
  describe('RentContract Management Update Component', () => {
    let comp: RentContractUpdateComponent;
    let fixture: ComponentFixture<RentContractUpdateComponent>;
    let service: RentContractService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ContractTestModule],
        declarations: [RentContractUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RentContractUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RentContractUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RentContractService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RentContract(123);
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
        const entity = new RentContract();
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
