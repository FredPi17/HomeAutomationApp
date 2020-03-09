import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HomeAutomationTestModule } from '../../../test.module';
import { MqttConnectionComponent } from 'app/entities/mqtt-connection/mqtt-connection.component';
import { MqttConnectionService } from 'app/entities/mqtt-connection/mqtt-connection.service';
import { MqttConnection } from 'app/shared/model/mqtt-connection.model';

describe('Component Tests', () => {
  describe('MqttConnection Management Component', () => {
    let comp: MqttConnectionComponent;
    let fixture: ComponentFixture<MqttConnectionComponent>;
    let service: MqttConnectionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HomeAutomationTestModule],
        declarations: [MqttConnectionComponent]
      })
        .overrideTemplate(MqttConnectionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MqttConnectionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MqttConnectionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new MqttConnection(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.mqttConnections && comp.mqttConnections[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
