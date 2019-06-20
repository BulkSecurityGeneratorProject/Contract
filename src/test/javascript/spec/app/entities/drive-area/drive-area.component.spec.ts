/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ContractTestModule } from '../../../test.module';
import { DriveAreaComponent } from 'app/entities/drive-area/drive-area.component';
import { DriveAreaService } from 'app/entities/drive-area/drive-area.service';
import { DriveArea } from 'app/shared/model/drive-area.model';

describe('Component Tests', () => {
  describe('DriveArea Management Component', () => {
    let comp: DriveAreaComponent;
    let fixture: ComponentFixture<DriveAreaComponent>;
    let service: DriveAreaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ContractTestModule],
        declarations: [DriveAreaComponent],
        providers: []
      })
        .overrideTemplate(DriveAreaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DriveAreaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DriveAreaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DriveArea(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.driveAreas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
