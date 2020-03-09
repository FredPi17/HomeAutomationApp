import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMqttConnection, MqttConnection } from 'app/shared/model/mqtt-connection.model';
import { MqttConnectionService } from './mqtt-connection.service';
import { MqttConnectionComponent } from './mqtt-connection.component';
import { MqttConnectionDetailComponent } from './mqtt-connection-detail.component';
import { MqttConnectionUpdateComponent } from './mqtt-connection-update.component';

@Injectable({ providedIn: 'root' })
export class MqttConnectionResolve implements Resolve<IMqttConnection> {
  constructor(private service: MqttConnectionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMqttConnection> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((mqttConnection: HttpResponse<MqttConnection>) => {
          if (mqttConnection.body) {
            return of(mqttConnection.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MqttConnection());
  }
}

export const mqttConnectionRoute: Routes = [
  {
    path: '',
    component: MqttConnectionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'homeAutomationApp.mqttConnection.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MqttConnectionDetailComponent,
    resolve: {
      mqttConnection: MqttConnectionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'homeAutomationApp.mqttConnection.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MqttConnectionUpdateComponent,
    resolve: {
      mqttConnection: MqttConnectionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'homeAutomationApp.mqttConnection.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MqttConnectionUpdateComponent,
    resolve: {
      mqttConnection: MqttConnectionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'homeAutomationApp.mqttConnection.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
