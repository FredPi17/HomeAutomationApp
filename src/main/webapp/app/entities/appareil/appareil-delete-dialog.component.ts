import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAppareil } from 'app/shared/model/appareil.model';
import { AppareilService } from './appareil.service';

@Component({
  templateUrl: './appareil-delete-dialog.component.html'
})
export class AppareilDeleteDialogComponent {
  appareil?: IAppareil;

  constructor(protected appareilService: AppareilService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.appareilService.delete(id).subscribe(() => {
      this.eventManager.broadcast('appareilListModification');
      this.activeModal.close();
    });
  }
}
