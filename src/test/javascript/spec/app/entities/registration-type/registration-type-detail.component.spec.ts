/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ContractTestModule } from '../../../test.module';
import { RegistrationTypeDetailComponent } from 'app/entities/registration-type/registration-type-detail.component';
import { RegistrationType } from 'app/shared/model/registration-type.model';

describe('Component Tests', () => {
  describe('RegistrationType Management Detail Component', () => {
    let comp: RegistrationTypeDetailComponent;
    let fixture: ComponentFixture<RegistrationTypeDetailComponent>;
    const route = ({ data: of({ registrationType: new RegistrationType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ContractTestModule],
        declarations: [RegistrationTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RegistrationTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RegistrationTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.registrationType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
