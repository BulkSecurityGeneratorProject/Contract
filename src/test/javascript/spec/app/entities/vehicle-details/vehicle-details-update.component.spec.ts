/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { ContractTestModule } from '../../../test.module';
import { VehicleDetailsUpdateComponent } from 'app/entities/vehicle-details/vehicle-details-update.component';
import { VehicleDetailsService } from 'app/entities/vehicle-details/vehicle-details.service';
import { VehicleDetails } from 'app/shared/model/vehicle-details.model';

describe('Component Tests', () => {
  describe('VehicleDetails Management Update Component', () => {
    let comp: VehicleDetailsUpdateComponent;
    let fixture: ComponentFixture<VehicleDetailsUpdateComponent>;
    let service: VehicleDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ContractTestModule],
        declarations: [VehicleDetailsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(VehicleDetailsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VehicleDetailsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VehicleDetailsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new VehicleDetails(123);
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
        const entity = new VehicleDetails();
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
