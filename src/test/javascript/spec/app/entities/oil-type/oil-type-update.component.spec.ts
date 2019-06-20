/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { ContractTestModule } from '../../../test.module';
import { OilTypeUpdateComponent } from 'app/entities/oil-type/oil-type-update.component';
import { OilTypeService } from 'app/entities/oil-type/oil-type.service';
import { OilType } from 'app/shared/model/oil-type.model';

describe('Component Tests', () => {
  describe('OilType Management Update Component', () => {
    let comp: OilTypeUpdateComponent;
    let fixture: ComponentFixture<OilTypeUpdateComponent>;
    let service: OilTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ContractTestModule],
        declarations: [OilTypeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(OilTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OilTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OilTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new OilType(123);
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
        const entity = new OilType();
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
