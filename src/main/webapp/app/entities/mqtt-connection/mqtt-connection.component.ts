import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMqttConnection } from 'app/shared/model/mqtt-connection.model';
import { MqttConnectionService } from './mqtt-connection.service';
import { MqttConnectionDeleteDialogComponent } from './mqtt-connection-delete-dialog.component';

@Component({
  selector: 'jhi-mqtt-connection',
  templateUrl: './mqtt-connection.component.html'
})
export class MqttConnectionComponent implements OnInit, OnDestroy {
  mqttConnections?: IMqttConnection[];
  eventSubscriber?: Subscription;

  constructor(
    protected mqttConnectionService: MqttConnectionService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.mqttConnectionService.query().subscribe((res: HttpResponse<IMqttConnection[]>) => (this.mqttConnections = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInMqttConnections();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IMqttConnection): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInMqttConnections(): void {
    this.eventSubscriber = this.eventManager.subscribe('mqttConnectionListModification', () => this.loadAll());
  }

  delete(mqttConnection: IMqttConnection): void {
    const modalRef = this.modalService.open(MqttConnectionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.mqttConnection = mqttConnection;
  }
}
