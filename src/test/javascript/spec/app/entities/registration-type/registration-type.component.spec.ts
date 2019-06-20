/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ContractTestModule } from '../../../test.module';
import { RegistrationTypeComponent } from 'app/entities/registration-type/registration-type.component';
import { RegistrationTypeService } from 'app/entities/registration-type/registration-type.service';
import { RegistrationType } from 'app/shared/model/registration-type.model';

describe('Component Tests', () => {
  describe('RegistrationType Management Component', () => {
    let comp: RegistrationTypeComponent;
    let fixture: ComponentFixture<RegistrationTypeComponent>;
    let service: RegistrationTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ContractTestModule],
        declarations: [RegistrationTypeComponent],
        providers: []
      })
        .overrideTemplate(RegistrationTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RegistrationTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RegistrationTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new RegistrationType(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.registrationTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
