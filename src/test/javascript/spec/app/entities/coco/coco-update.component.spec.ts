/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { ContractTestModule } from '../../../test.module';
import { COCOUpdateComponent } from 'app/entities/coco/coco-update.component';
import { COCOService } from 'app/entities/coco/coco.service';
import { COCO } from 'app/shared/model/coco.model';

describe('Component Tests', () => {
  describe('COCO Management Update Component', () => {
    let comp: COCOUpdateComponent;
    let fixture: ComponentFixture<COCOUpdateComponent>;
    let service: COCOService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ContractTestModule],
        declarations: [COCOUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(COCOUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(COCOUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(COCOService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new COCO(123);
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
        const entity = new COCO();
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
