import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMqttConnection } from 'app/shared/model/mqtt-connection.model';
import { MqttConnectionService } from './mqtt-connection.service';

@Component({
  templateUrl: './mqtt-connection-delete-dialog.component.html'
})
export class MqttConnectionDeleteDialogComponent {
  mqttConnection?: IMqttConnection;

  constructor(
    protected mqttConnectionService: MqttConnectionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.mqttConnectionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('mqttConnectionListModification');
      this.activeModal.close();
    });
  }
}
