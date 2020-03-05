import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypeAppareil } from 'app/shared/model/type-appareil.model';
import { TypeAppareilService } from './type-appareil.service';
import { TypeAppareilDeleteDialogComponent } from './type-appareil-delete-dialog.component';

@Component({
  selector: 'jhi-type-appareil',
  templateUrl: './type-appareil.component.html'
})
export class TypeAppareilComponent implements OnInit, OnDestroy {
  typeAppareils?: ITypeAppareil[];
  eventSubscriber?: Subscription;

  constructor(
    protected typeAppareilService: TypeAppareilService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.typeAppareilService.query().subscribe((res: HttpResponse<ITypeAppareil[]>) => (this.typeAppareils = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTypeAppareils();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITypeAppareil): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTypeAppareils(): void {
    this.eventSubscriber = this.eventManager.subscribe('typeAppareilListModification', () => this.loadAll());
  }

  delete(typeAppareil: ITypeAppareil): void {
    const modalRef = this.modalService.open(TypeAppareilDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.typeAppareil = typeAppareil;
  }
}
