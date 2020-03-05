import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HomeAutomationSharedModule } from 'app/shared/shared.module';
import { MqttConnectionComponent } from './mqtt-connection.component';
import { MqttConnectionDetailComponent } from './mqtt-connection-detail.component';
import { MqttConnectionUpdateComponent } from './mqtt-connection-update.component';
import { MqttConnectionDeleteDialogComponent } from './mqtt-connection-delete-dialog.component';
import { mqttConnectionRoute } from './mqtt-connection.route';

@NgModule({
  imports: [HomeAutomationSharedModule, RouterModule.forChild(mqttConnectionRoute)],
  declarations: [
    MqttConnectionComponent,
    MqttConnectionDetailComponent,
    MqttConnectionUpdateComponent,
    MqttConnectionDeleteDialogComponent
  ],
  entryComponents: [MqttConnectionDeleteDialogComponent]
})
export class HomeAutomationMqttConnectionModule {}
