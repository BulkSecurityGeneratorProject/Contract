/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ContractTestModule } from '../../../test.module';
import { LicenseTypeComponent } from 'app/entities/license-type/license-type.component';
import { LicenseTypeService } from 'app/entities/license-type/license-type.service';
import { LicenseType } from 'app/shared/model/license-type.model';

describe('Component Tests', () => {
  describe('LicenseType Management Component', () => {
    let comp: LicenseTypeComponent;
    let fixture: ComponentFixture<LicenseTypeComponent>;
    let service: LicenseTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ContractTestModule],
        declarations: [LicenseTypeComponent],
        providers: []
      })
        .overrideTemplate(LicenseTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LicenseTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LicenseTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new LicenseType(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.licenseTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
