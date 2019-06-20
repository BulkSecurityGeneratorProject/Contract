/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ContractTestModule } from '../../../test.module';
import { PersonInfoDetailComponent } from 'app/entities/person-info/person-info-detail.component';
import { PersonInfo } from 'app/shared/model/person-info.model';

describe('Component Tests', () => {
  describe('PersonInfo Management Detail Component', () => {
    let comp: PersonInfoDetailComponent;
    let fixture: ComponentFixture<PersonInfoDetailComponent>;
    const route = ({ data: of({ personInfo: new PersonInfo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ContractTestModule],
        declarations: [PersonInfoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PersonInfoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PersonInfoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.personInfo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
