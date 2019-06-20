/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ContractTestModule } from '../../../test.module';
import { OilTypeComponent } from 'app/entities/oil-type/oil-type.component';
import { OilTypeService } from 'app/entities/oil-type/oil-type.service';
import { OilType } from 'app/shared/model/oil-type.model';

describe('Component Tests', () => {
  describe('OilType Management Component', () => {
    let comp: OilTypeComponent;
    let fixture: ComponentFixture<OilTypeComponent>;
    let service: OilTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ContractTestModule],
        declarations: [OilTypeComponent],
        providers: []
      })
        .overrideTemplate(OilTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OilTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OilTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new OilType(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.oilTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
