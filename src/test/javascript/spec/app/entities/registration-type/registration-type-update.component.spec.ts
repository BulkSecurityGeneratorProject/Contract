/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { ContractTestModule } from '../../../test.module';
import { RegistrationTypeUpdateComponent } from 'app/entities/registration-type/registration-type-update.component';
import { RegistrationTypeService } from 'app/entities/registration-type/registration-type.service';
import { RegistrationType } from 'app/shared/model/registration-type.model';

describe('Component Tests', () => {
  describe('RegistrationType Management Update Component', () => {
    let comp: RegistrationTypeUpdateComponent;
    let fixture: ComponentFixture<RegistrationTypeUpdateComponent>;
    let service: RegistrationTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ContractTestModule],
        declarations: [RegistrationTypeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RegistrationTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RegistrationTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RegistrationTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RegistrationType(123);
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
        const entity = new RegistrationType();
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
