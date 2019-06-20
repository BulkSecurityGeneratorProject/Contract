/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ContractTestModule } from '../../../test.module';
import { IdTypeDetailComponent } from 'app/entities/id-type/id-type-detail.component';
import { IdType } from 'app/shared/model/id-type.model';

describe('Component Tests', () => {
  describe('IdType Management Detail Component', () => {
    let comp: IdTypeDetailComponent;
    let fixture: ComponentFixture<IdTypeDetailComponent>;
    const route = ({ data: of({ idType: new IdType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ContractTestModule],
        declarations: [IdTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(IdTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IdTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.idType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
