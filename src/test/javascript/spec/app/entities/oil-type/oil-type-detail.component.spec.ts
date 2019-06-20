/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ContractTestModule } from '../../../test.module';
import { OilTypeDetailComponent } from 'app/entities/oil-type/oil-type-detail.component';
import { OilType } from 'app/shared/model/oil-type.model';

describe('Component Tests', () => {
  describe('OilType Management Detail Component', () => {
    let comp: OilTypeDetailComponent;
    let fixture: ComponentFixture<OilTypeDetailComponent>;
    const route = ({ data: of({ oilType: new OilType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ContractTestModule],
        declarations: [OilTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OilTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OilTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.oilType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
