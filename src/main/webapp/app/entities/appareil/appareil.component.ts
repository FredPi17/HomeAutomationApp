import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAppareil } from 'app/shared/model/appareil.model';
import { AppareilService } from './appareil.service';
import { AppareilDeleteDialogComponent } from './appareil-delete-dialog.component';

@Component({
  selector: 'jhi-appareil',
  templateUrl: './appareil.component.html'
})
export class AppareilComponent implements OnInit, OnDestroy {
  appareils?: IAppareil[];
  eventSubscriber?: Subscription;

  constructor(protected appareilService: AppareilService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.appareilService.query().subscribe((res: HttpResponse<IAppareil[]>) => (this.appareils = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAppareils();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAppareil): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAppareils(): void {
    this.eventSubscriber = this.eventManager.subscribe('appareilListModification', () => this.loadAll());
  }

  delete(appareil: IAppareil): void {
    const modalRef = this.modalService.open(AppareilDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.appareil = appareil;
  }
}
