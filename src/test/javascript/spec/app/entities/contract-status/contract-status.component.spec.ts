/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ContractTestModule } from '../../../test.module';
import { ContractStatusComponent } from 'app/entities/contract-status/contract-status.component';
import { ContractStatusService } from 'app/entities/contract-status/contract-status.service';
import { ContractStatus } from 'app/shared/model/contract-status.model';

describe('Component Tests', () => {
  describe('ContractStatus Management Component', () => {
    let comp: ContractStatusComponent;
    let fixture: ComponentFixture<ContractStatusComponent>;
    let service: ContractStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ContractTestModule],
        declarations: [ContractStatusComponent],
        providers: []
      })
        .overrideTemplate(ContractStatusComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContractStatusComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContractStatusService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ContractStatus(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.contractStatuses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
