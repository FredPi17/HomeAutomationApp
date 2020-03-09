import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HomeAutomationTestModule } from '../../../test.module';
import { MqttConnectionUpdateComponent } from 'app/entities/mqtt-connection/mqtt-connection-update.component';
import { MqttConnectionService } from 'app/entities/mqtt-connection/mqtt-connection.service';
import { MqttConnection } from 'app/shared/model/mqtt-connection.model';

describe('Component Tests', () => {
  describe('MqttConnection Management Update Component', () => {
    let comp: MqttConnectionUpdateComponent;
    let fixture: ComponentFixture<MqttConnectionUpdateComponent>;
    let service: MqttConnectionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HomeAutomationTestModule],
        declarations: [MqttConnectionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MqttConnectionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MqttConnectionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MqttConnectionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MqttConnection(123);
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
        const entity = new MqttConnection();
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
