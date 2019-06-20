/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ContractTestModule } from '../../../test.module';
import { FuelTypeDetailComponent } from 'app/entities/fuel-type/fuel-type-detail.component';
import { FuelType } from 'app/shared/model/fuel-type.model';

describe('Component Tests', () => {
  describe('FuelType Management Detail Component', () => {
    let comp: FuelTypeDetailComponent;
    let fixture: ComponentFixture<FuelTypeDetailComponent>;
    const route = ({ data: of({ fuelType: new FuelType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ContractTestModule],
        declarations: [FuelTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FuelTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FuelTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fuelType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
