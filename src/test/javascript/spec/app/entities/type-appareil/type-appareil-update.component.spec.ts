import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HomeAutomationTestModule } from '../../../test.module';
import { TypeAppareilUpdateComponent } from 'app/entities/type-appareil/type-appareil-update.component';
import { TypeAppareilService } from 'app/entities/type-appareil/type-appareil.service';
import { TypeAppareil } from 'app/shared/model/type-appareil.model';

describe('Component Tests', () => {
  describe('TypeAppareil Management Update Component', () => {
    let comp: TypeAppareilUpdateComponent;
    let fixture: ComponentFixture<TypeAppareilUpdateComponent>;
    let service: TypeAppareilService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HomeAutomationTestModule],
        declarations: [TypeAppareilUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TypeAppareilUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeAppareilUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeAppareilService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypeAppareil(123);
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
        const entity = new TypeAppareil();
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
