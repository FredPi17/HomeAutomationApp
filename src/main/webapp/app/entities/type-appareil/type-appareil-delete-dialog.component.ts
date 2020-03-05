import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeAppareil } from 'app/shared/model/type-appareil.model';
import { TypeAppareilService } from './type-appareil.service';

@Component({
  templateUrl: './type-appareil-delete-dialog.component.html'
})
export class TypeAppareilDeleteDialogComponent {
  typeAppareil?: ITypeAppareil;

  constructor(
    protected typeAppareilService: TypeAppareilService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeAppareilService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typeAppareilListModification');
      this.activeModal.close();
    });
  }
}
