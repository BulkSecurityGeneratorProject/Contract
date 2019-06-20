/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ContractTestModule } from '../../../test.module';
import { ContractStatusDetailComponent } from 'app/entities/contract-status/contract-status-detail.component';
import { ContractStatus } from 'app/shared/model/contract-status.model';

describe('Component Tests', () => {
  describe('ContractStatus Management Detail Component', () => {
    let comp: ContractStatusDetailComponent;
    let fixture: ComponentFixture<ContractStatusDetailComponent>;
    const route = ({ data: of({ contractStatus: new ContractStatus(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ContractTestModule],
        declarations: [ContractStatusDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ContractStatusDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContractStatusDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.contractStatus).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
