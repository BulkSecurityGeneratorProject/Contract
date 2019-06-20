/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { ContractTestModule } from '../../../test.module';
import { RentalAccountUpdateComponent } from 'app/entities/rental-account/rental-account-update.component';
import { RentalAccountService } from 'app/entities/rental-account/rental-account.service';
import { RentalAccount } from 'app/shared/model/rental-account.model';

describe('Component Tests', () => {
  describe('RentalAccount Management Update Component', () => {
    let comp: RentalAccountUpdateComponent;
    let fixture: ComponentFixture<RentalAccountUpdateComponent>;
    let service: RentalAccountService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ContractTestModule],
        declarations: [RentalAccountUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RentalAccountUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RentalAccountUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RentalAccountService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RentalAccount(123);
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
        const entity = new RentalAccount();
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
