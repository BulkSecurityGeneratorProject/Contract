/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ContractTestModule } from '../../../test.module';
import { FuelTypeComponent } from 'app/entities/fuel-type/fuel-type.component';
import { FuelTypeService } from 'app/entities/fuel-type/fuel-type.service';
import { FuelType } from 'app/shared/model/fuel-type.model';

describe('Component Tests', () => {
  describe('FuelType Management Component', () => {
    let comp: FuelTypeComponent;
    let fixture: ComponentFixture<FuelTypeComponent>;
    let service: FuelTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ContractTestModule],
        declarations: [FuelTypeComponent],
        providers: []
      })
        .overrideTemplate(FuelTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FuelTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FuelTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new FuelType(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.fuelTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
