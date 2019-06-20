/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ContractTestModule } from '../../../test.module';
import { ContractTypeComponent } from 'app/entities/contract-type/contract-type.component';
import { ContractTypeService } from 'app/entities/contract-type/contract-type.service';
import { ContractType } from 'app/shared/model/contract-type.model';

describe('Component Tests', () => {
  describe('ContractType Management Component', () => {
    let comp: ContractTypeComponent;
    let fixture: ComponentFixture<ContractTypeComponent>;
    let service: ContractTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ContractTestModule],
        declarations: [ContractTypeComponent],
        providers: []
      })
        .overrideTemplate(ContractTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContractTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContractTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ContractType(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.contractTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
