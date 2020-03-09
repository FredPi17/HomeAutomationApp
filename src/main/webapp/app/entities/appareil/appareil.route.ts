import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAppareil, Appareil } from 'app/shared/model/appareil.model';
import { AppareilService } from './appareil.service';
import { AppareilComponent } from './appareil.component';
import { AppareilDetailComponent } from './appareil-detail.component';
import { AppareilUpdateComponent } from './appareil-update.component';

@Injectable({ providedIn: 'root' })
export class AppareilResolve implements Resolve<IAppareil> {
  constructor(private service: AppareilService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAppareil> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((appareil: HttpResponse<Appareil>) => {
          if (appareil.body) {
            return of(appareil.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Appareil());
  }
}

export const appareilRoute: Routes = [
  {
    path: '',
    component: AppareilComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'homeAutomationApp.appareil.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AppareilDetailComponent,
    resolve: {
      appareil: AppareilResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'homeAutomationApp.appareil.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AppareilUpdateComponent,
    resolve: {
      appareil: AppareilResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'homeAutomationApp.appareil.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AppareilUpdateComponent,
    resolve: {
      appareil: AppareilResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'homeAutomationApp.appareil.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
