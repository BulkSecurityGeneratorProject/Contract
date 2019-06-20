/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { ContractTestModule } from '../../../test.module';
import { FuelTypeUpdateComponent } from 'app/entities/fuel-type/fuel-type-update.component';
import { FuelTypeService } from 'app/entities/fuel-type/fuel-type.service';
import { FuelType } from 'app/shared/model/fuel-type.model';

describe('Component Tests', () => {
  describe('FuelType Management Update Component', () => {
    let comp: FuelTypeUpdateComponent;
    let fixture: ComponentFixture<FuelTypeUpdateComponent>;
    let service: FuelTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ContractTestModule],
        declarations: [FuelTypeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FuelTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FuelTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FuelTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FuelType(123);
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
        const entity = new FuelType();
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
