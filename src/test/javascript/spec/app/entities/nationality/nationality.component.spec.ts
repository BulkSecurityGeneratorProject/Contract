/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ContractTestModule } from '../../../test.module';
import { NationalityComponent } from 'app/entities/nationality/nationality.component';
import { NationalityService } from 'app/entities/nationality/nationality.service';
import { Nationality } from 'app/shared/model/nationality.model';

describe('Component Tests', () => {
  describe('Nationality Management Component', () => {
    let comp: NationalityComponent;
    let fixture: ComponentFixture<NationalityComponent>;
    let service: NationalityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ContractTestModule],
        declarations: [NationalityComponent],
        providers: []
      })
        .overrideTemplate(NationalityComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NationalityComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NationalityService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Nationality(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.nationalities[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
