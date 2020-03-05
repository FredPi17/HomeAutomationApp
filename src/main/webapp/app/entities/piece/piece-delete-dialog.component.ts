import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPiece } from 'app/shared/model/piece.model';
import { PieceService } from './piece.service';

@Component({
  templateUrl: './piece-delete-dialog.component.html'
})
export class PieceDeleteDialogComponent {
  piece?: IPiece;

  constructor(protected pieceService: PieceService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pieceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('pieceListModification');
      this.activeModal.close();
    });
  }
}
