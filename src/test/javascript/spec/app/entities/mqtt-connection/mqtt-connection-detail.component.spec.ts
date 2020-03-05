import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HomeAutomationTestModule } from '../../../test.module';
import { MqttConnectionDetailComponent } from 'app/entities/mqtt-connection/mqtt-connection-detail.component';
import { MqttConnection } from 'app/shared/model/mqtt-connection.model';

describe('Component Tests', () => {
  describe('MqttConnection Management Detail Component', () => {
    let comp: MqttConnectionDetailComponent;
    let fixture: ComponentFixture<MqttConnectionDetailComponent>;
    const route = ({ data: of({ mqttConnection: new MqttConnection(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HomeAutomationTestModule],
        declarations: [MqttConnectionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MqttConnectionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MqttConnectionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load mqttConnection on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.mqttConnection).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
