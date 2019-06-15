/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ContractTestModule } from '../../../test.module';
import { COCODetailComponent } from 'app/entities/coco/coco-detail.component';
import { COCO } from 'app/shared/model/coco.model';

describe('Component Tests', () => {
  describe('COCO Management Detail Component', () => {
    let comp: COCODetailComponent;
    let fixture: ComponentFixture<COCODetailComponent>;
    const route = ({ data: of({ cOCO: new COCO(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ContractTestModule],
        declarations: [COCODetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(COCODetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(COCODetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cOCO).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
