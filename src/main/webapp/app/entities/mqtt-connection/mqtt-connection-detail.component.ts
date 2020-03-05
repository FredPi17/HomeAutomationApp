import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMqttConnection } from 'app/shared/model/mqtt-connection.model';

@Component({
  selector: 'jhi-mqtt-connection-detail',
  templateUrl: './mqtt-connection-detail.component.html'
})
export class MqttConnectionDetailComponent implements OnInit {
  mqttConnection: IMqttConnection | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mqttConnection }) => (this.mqttConnection = mqttConnection));
  }

  previousState(): void {
    window.history.back();
  }
}
