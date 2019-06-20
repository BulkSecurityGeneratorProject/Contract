/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ContractTestModule } from '../../../test.module';
import { VehicleItemStatusComponent } from 'app/entities/vehicle-item-status/vehicle-item-status.component';
import { VehicleItemStatusService } from 'app/entities/vehicle-item-status/vehicle-item-status.service';
import { VehicleItemStatus } from 'app/shared/model/vehicle-item-status.model';

describe('Component Tests', () => {
  describe('VehicleItemStatus Management Component', () => {
    let comp: VehicleItemStatusComponent;
    let fixture: ComponentFixture<VehicleItemStatusComponent>;
    let service: VehicleItemStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ContractTestModule],
        declarations: [VehicleItemStatusComponent],
        providers: []
      })
        .overrideTemplate(VehicleItemStatusComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VehicleItemStatusComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VehicleItemStatusService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new VehicleItemStatus(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.vehicleItemStatuses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
