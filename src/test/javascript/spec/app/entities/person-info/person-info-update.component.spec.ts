/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { ContractTestModule } from '../../../test.module';
import { PersonInfoUpdateComponent } from 'app/entities/person-info/person-info-update.component';
import { PersonInfoService } from 'app/entities/person-info/person-info.service';
import { PersonInfo } from 'app/shared/model/person-info.model';

describe('Component Tests', () => {
  describe('PersonInfo Management Update Component', () => {
    let comp: PersonInfoUpdateComponent;
    let fixture: ComponentFixture<PersonInfoUpdateComponent>;
    let service: PersonInfoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ContractTestModule],
        declarations: [PersonInfoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PersonInfoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PersonInfoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PersonInfoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PersonInfo(123);
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
        const entity = new PersonInfo();
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
