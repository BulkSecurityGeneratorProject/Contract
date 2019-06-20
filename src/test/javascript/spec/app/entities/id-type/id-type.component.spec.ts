/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ContractTestModule } from '../../../test.module';
import { IdTypeComponent } from 'app/entities/id-type/id-type.component';
import { IdTypeService } from 'app/entities/id-type/id-type.service';
import { IdType } from 'app/shared/model/id-type.model';

describe('Component Tests', () => {
  describe('IdType Management Component', () => {
    let comp: IdTypeComponent;
    let fixture: ComponentFixture<IdTypeComponent>;
    let service: IdTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ContractTestModule],
        declarations: [IdTypeComponent],
        providers: []
      })
        .overrideTemplate(IdTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IdTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IdTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new IdType(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.idTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
