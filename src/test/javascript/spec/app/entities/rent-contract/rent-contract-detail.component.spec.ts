/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ContractTestModule } from '../../../test.module';
import { RentContractDetailComponent } from 'app/entities/rent-contract/rent-contract-detail.component';
import { RentContract } from 'app/shared/model/rent-contract.model';

describe('Component Tests', () => {
  describe('RentContract Management Detail Component', () => {
    let comp: RentContractDetailComponent;
    let fixture: ComponentFixture<RentContractDetailComponent>;
    const route = ({ data: of({ rentContract: new RentContract(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ContractTestModule],
        declarations: [RentContractDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RentContractDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RentContractDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.rentContract).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
