import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HomeAutomationTestModule } from '../../../test.module';
import { AppareilUpdateComponent } from 'app/entities/appareil/appareil-update.component';
import { AppareilService } from 'app/entities/appareil/appareil.service';
import { Appareil } from 'app/shared/model/appareil.model';

describe('Component Tests', () => {
  describe('Appareil Management Update Component', () => {
    let comp: AppareilUpdateComponent;
    let fixture: ComponentFixture<AppareilUpdateComponent>;
    let service: AppareilService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HomeAutomationTestModule],
        declarations: [AppareilUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AppareilUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AppareilUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AppareilService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Appareil(123);
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
        const entity = new Appareil();
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
