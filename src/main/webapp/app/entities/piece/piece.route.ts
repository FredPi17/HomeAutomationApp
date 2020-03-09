import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPiece, Piece } from 'app/shared/model/piece.model';
import { PieceService } from './piece.service';
import { PieceComponent } from './piece.component';
import { PieceDetailComponent } from './piece-detail.component';
import { PieceUpdateComponent } from './piece-update.component';

@Injectable({ providedIn: 'root' })
export class PieceResolve implements Resolve<IPiece> {
  constructor(private service: PieceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPiece> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((piece: HttpResponse<Piece>) => {
          if (piece.body) {
            return of(piece.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Piece());
  }
}

export const pieceRoute: Routes = [
  {
    path: '',
    component: PieceComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'homeAutomationApp.piece.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PieceDetailComponent,
    resolve: {
      piece: PieceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'homeAutomationApp.piece.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PieceUpdateComponent,
    resolve: {
      piece: PieceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'homeAutomationApp.piece.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PieceUpdateComponent,
    resolve: {
      piece: PieceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'homeAutomationApp.piece.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
