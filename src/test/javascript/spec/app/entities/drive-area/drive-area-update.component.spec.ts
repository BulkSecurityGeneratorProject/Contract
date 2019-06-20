/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { ContractTestModule } from '../../../test.module';
import { DriveAreaUpdateComponent } from 'app/entities/drive-area/drive-area-update.component';
import { DriveAreaService } from 'app/entities/drive-area/drive-area.service';
import { DriveArea } from 'app/shared/model/drive-area.model';

describe('Component Tests', () => {
  describe('DriveArea Management Update Component', () => {
    let comp: DriveAreaUpdateComponent;
    let fixture: ComponentFixture<DriveAreaUpdateComponent>;
    let service: DriveAreaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ContractTestModule],
        declarations: [DriveAreaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DriveAreaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DriveAreaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DriveAreaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DriveArea(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new DriveArea();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
