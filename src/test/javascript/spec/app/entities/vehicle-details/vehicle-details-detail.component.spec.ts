/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ContractTestModule } from '../../../test.module';
import { VehicleDetailsDetailComponent } from 'app/entities/vehicle-details/vehicle-details-detail.component';
import { VehicleDetails } from 'app/shared/model/vehicle-details.model';

describe('Component Tests', () => {
  describe('VehicleDetails Management Detail Component', () => {
    let comp: VehicleDetailsDetailComponent;
    let fixture: ComponentFixture<VehicleDetailsDetailComponent>;
    const route = ({ data: of({ vehicleDetails: new VehicleDetails(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ContractTestModule],
        declarations: [VehicleDetailsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(VehicleDetailsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VehicleDetailsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.vehicleDetails).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
