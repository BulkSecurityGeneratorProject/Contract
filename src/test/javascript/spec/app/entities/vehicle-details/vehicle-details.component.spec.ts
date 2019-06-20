/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ContractTestModule } from '../../../test.module';
import { VehicleDetailsComponent } from 'app/entities/vehicle-details/vehicle-details.component';
import { VehicleDetailsService } from 'app/entities/vehicle-details/vehicle-details.service';
import { VehicleDetails } from 'app/shared/model/vehicle-details.model';

describe('Component Tests', () => {
  describe('VehicleDetails Management Component', () => {
    let comp: VehicleDetailsComponent;
    let fixture: ComponentFixture<VehicleDetailsComponent>;
    let service: VehicleDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ContractTestModule],
        declarations: [VehicleDetailsComponent],
        providers: []
      })
        .overrideTemplate(VehicleDetailsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VehicleDetailsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VehicleDetailsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new VehicleDetails(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.vehicleDetails[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
