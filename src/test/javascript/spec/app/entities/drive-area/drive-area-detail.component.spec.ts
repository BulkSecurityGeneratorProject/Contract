/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ContractTestModule } from '../../../test.module';
import { DriveAreaDetailComponent } from 'app/entities/drive-area/drive-area-detail.component';
import { DriveArea } from 'app/shared/model/drive-area.model';

describe('Component Tests', () => {
  describe('DriveArea Management Detail Component', () => {
    let comp: DriveAreaDetailComponent;
    let fixture: ComponentFixture<DriveAreaDetailComponent>;
    const route = ({ data: of({ driveArea: new DriveArea(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ContractTestModule],
        declarations: [DriveAreaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DriveAreaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DriveAreaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.driveArea).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
