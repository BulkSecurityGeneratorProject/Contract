/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ContractTestModule } from '../../../test.module';
import { RentalAccountDetailComponent } from 'app/entities/rental-account/rental-account-detail.component';
import { RentalAccount } from 'app/shared/model/rental-account.model';

describe('Component Tests', () => {
  describe('RentalAccount Management Detail Component', () => {
    let comp: RentalAccountDetailComponent;
    let fixture: ComponentFixture<RentalAccountDetailComponent>;
    const route = ({ data: of({ rentalAccount: new RentalAccount(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ContractTestModule],
        declarations: [RentalAccountDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RentalAccountDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RentalAccountDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.rentalAccount).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
