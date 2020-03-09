import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'piece',
        loadChildren: () => import('./piece/piece.module').then(m => m.HomeAutomationPieceModule)
      },
      {
        path: 'type-appareil',
        loadChildren: () => import('./type-appareil/type-appareil.module').then(m => m.HomeAutomationTypeAppareilModule)
      },
      {
        path: 'mqtt-connection',
        loadChildren: () => import('./mqtt-connection/mqtt-connection.module').then(m => m.HomeAutomationMqttConnectionModule)
      },
      {
        path: 'appareil',
        loadChildren: () => import('./appareil/appareil.module').then(m => m.HomeAutomationAppareilModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class HomeAutomationEntityModule {}
