import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMqttConnection, MqttConnection } from 'app/shared/model/mqtt-connection.model';
import { MqttConnectionService } from './mqtt-connection.service';

@Component({
  selector: 'jhi-mqtt-connection-update',
  templateUrl: './mqtt-connection-update.component.html'
})
export class MqttConnectionUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    urlServeur: [],
    port: [],
    username: [],
    password: [],
    topic: [null, [Validators.required]]
  });

  constructor(protected mqttConnectionService: MqttConnectionService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mqttConnection }) => {
      this.updateForm(mqttConnection);
    });
  }

  updateForm(mqttConnection: IMqttConnection): void {
    this.editForm.patchValue({
      id: mqttConnection.id,
      name: mqttConnection.name,
      urlServeur: mqttConnection.urlServeur,
      port: mqttConnection.port,
      username: mqttConnection.username,
      password: mqttConnection.password,
      topic: mqttConnection.topic
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const mqttConnection = this.createFromForm();
    if (mqttConnection.id !== undefined) {
      this.subscribeToSaveResponse(this.mqttConnectionService.update(mqttConnection));
    } else {
      this.subscribeToSaveResponse(this.mqttConnectionService.create(mqttConnection));
    }
  }

  private createFromForm(): IMqttConnection {
    return {
      ...new MqttConnection(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      urlServeur: this.editForm.get(['urlServeur'])!.value,
      port: this.editForm.get(['port'])!.value,
      username: this.editForm.get(['username'])!.value,
      password: this.editForm.get(['password'])!.value,
      topic: this.editForm.get(['topic'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMqttConnection>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
