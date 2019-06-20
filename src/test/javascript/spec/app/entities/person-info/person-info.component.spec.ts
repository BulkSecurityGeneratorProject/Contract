/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ContractTestModule } from '../../../test.module';
import { PersonInfoComponent } from 'app/entities/person-info/person-info.component';
import { PersonInfoService } from 'app/entities/person-info/person-info.service';
import { PersonInfo } from 'app/shared/model/person-info.model';

describe('Component Tests', () => {
  describe('PersonInfo Management Component', () => {
    let comp: PersonInfoComponent;
    let fixture: ComponentFixture<PersonInfoComponent>;
    let service: PersonInfoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ContractTestModule],
        declarations: [PersonInfoComponent],
        providers: []
      })
        .overrideTemplate(PersonInfoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PersonInfoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PersonInfoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PersonInfo(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.personInfos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
