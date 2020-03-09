import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypeAppareil, TypeAppareil } from 'app/shared/model/type-appareil.model';
import { TypeAppareilService } from './type-appareil.service';
import { TypeAppareilComponent } from './type-appareil.component';
import { TypeAppareilDetailComponent } from './type-appareil-detail.component';
import { TypeAppareilUpdateComponent } from './type-appareil-update.component';

@Injectable({ providedIn: 'root' })
export class TypeAppareilResolve implements Resolve<ITypeAppareil> {
  constructor(private service: TypeAppareilService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeAppareil> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typeAppareil: HttpResponse<TypeAppareil>) => {
          if (typeAppareil.body) {
            return of(typeAppareil.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypeAppareil());
  }
}

export const typeAppareilRoute: Routes = [
  {
    path: '',
    component: TypeAppareilComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'homeAutomationApp.typeAppareil.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TypeAppareilDetailComponent,
    resolve: {
      typeAppareil: TypeAppareilResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'homeAutomationApp.typeAppareil.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TypeAppareilUpdateComponent,
    resolve: {
      typeAppareil: TypeAppareilResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'homeAutomationApp.typeAppareil.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TypeAppareilUpdateComponent,
    resolve: {
      typeAppareil: TypeAppareilResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'homeAutomationApp.typeAppareil.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
