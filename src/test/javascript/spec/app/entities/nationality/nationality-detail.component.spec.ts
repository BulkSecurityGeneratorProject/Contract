/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ContractTestModule } from '../../../test.module';
import { NationalityDetailComponent } from 'app/entities/nationality/nationality-detail.component';
import { Nationality } from 'app/shared/model/nationality.model';

describe('Component Tests', () => {
  describe('Nationality Management Detail Component', () => {
    let comp: NationalityDetailComponent;
    let fixture: ComponentFixture<NationalityDetailComponent>;
    const route = ({ data: of({ nationality: new Nationality(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ContractTestModule],
        declarations: [NationalityDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(NationalityDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NationalityDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.nationality).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
