/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ContractTestModule } from '../../../test.module';
import { RentalAccountComponent } from 'app/entities/rental-account/rental-account.component';
import { RentalAccountService } from 'app/entities/rental-account/rental-account.service';
import { RentalAccount } from 'app/shared/model/rental-account.model';

describe('Component Tests', () => {
  describe('RentalAccount Management Component', () => {
    let comp: RentalAccountComponent;
    let fixture: ComponentFixture<RentalAccountComponent>;
    let service: RentalAccountService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ContractTestModule],
        declarations: [RentalAccountComponent],
        providers: []
      })
        .overrideTemplate(RentalAccountComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RentalAccountComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RentalAccountService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new RentalAccount(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.rentalAccounts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
