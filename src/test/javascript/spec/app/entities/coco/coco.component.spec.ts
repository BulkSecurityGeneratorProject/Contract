/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ContractTestModule } from '../../../test.module';
import { COCOComponent } from 'app/entities/coco/coco.component';
import { COCOService } from 'app/entities/coco/coco.service';
import { COCO } from 'app/shared/model/coco.model';

describe('Component Tests', () => {
  describe('COCO Management Component', () => {
    let comp: COCOComponent;
    let fixture: ComponentFixture<COCOComponent>;
    let service: COCOService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ContractTestModule],
        declarations: [COCOComponent],
        providers: []
      })
        .overrideTemplate(COCOComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(COCOComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(COCOService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new COCO(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.cOCOS[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
