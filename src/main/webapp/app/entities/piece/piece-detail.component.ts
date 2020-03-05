import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPiece } from 'app/shared/model/piece.model';

@Component({
  selector: 'jhi-piece-detail',
  templateUrl: './piece-detail.component.html'
})
export class PieceDetailComponent implements OnInit {
  piece: IPiece | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ piece }) => (this.piece = piece));
  }

  previousState(): void {
    window.history.back();
  }
}
