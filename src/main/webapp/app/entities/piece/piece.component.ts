import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPiece } from 'app/shared/model/piece.model';
import { PieceService } from './piece.service';
import { PieceDeleteDialogComponent } from './piece-delete-dialog.component';

@Component({
  selector: 'jhi-piece',
  templateUrl: './piece.component.html'
})
export class PieceComponent implements OnInit, OnDestroy {
  pieces?: IPiece[];
  eventSubscriber?: Subscription;

  constructor(protected pieceService: PieceService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.pieceService.query().subscribe((res: HttpResponse<IPiece[]>) => (this.pieces = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPieces();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPiece): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPieces(): void {
    this.eventSubscriber = this.eventManager.subscribe('pieceListModification', () => this.loadAll());
  }

  delete(piece: IPiece): void {
    const modalRef = this.modalService.open(PieceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.piece = piece;
  }
}
