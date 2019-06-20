/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ContractTestModule } from '../../../test.module';
import { VehicleTypeComponent } from 'app/entities/vehicle-type/vehicle-type.component';
import { VehicleTypeService } from 'app/entities/vehicle-type/vehicle-type.service';
import { VehicleType } from 'app/shared/model/vehicle-type.model';

describe('Component Tests', () => {
  describe('VehicleType Management Component', () => {
    let comp: VehicleTypeComponent;
    let fixture: ComponentFixture<VehicleTypeComponent>;
    let service: VehicleTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ContractTestModule],
        declarations: [VehicleTypeComponent],
        providers: []
      })
        .overrideTemplate(VehicleTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VehicleTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VehicleTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new VehicleType(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.vehicleTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
