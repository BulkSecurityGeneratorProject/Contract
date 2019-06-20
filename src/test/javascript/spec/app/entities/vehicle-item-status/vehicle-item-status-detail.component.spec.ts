/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ContractTestModule } from '../../../test.module';
import { VehicleItemStatusDetailComponent } from 'app/entities/vehicle-item-status/vehicle-item-status-detail.component';
import { VehicleItemStatus } from 'app/shared/model/vehicle-item-status.model';

describe('Component Tests', () => {
  describe('VehicleItemStatus Management Detail Component', () => {
    let comp: VehicleItemStatusDetailComponent;
    let fixture: ComponentFixture<VehicleItemStatusDetailComponent>;
    const route = ({ data: of({ vehicleItemStatus: new VehicleItemStatus(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ContractTestModule],
        declarations: [VehicleItemStatusDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(VehicleItemStatusDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VehicleItemStatusDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.vehicleItemStatus).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
