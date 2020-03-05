import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAppareil } from 'app/shared/model/appareil.model';

@Component({
  selector: 'jhi-appareil-detail',
  templateUrl: './appareil-detail.component.html'
})
export class AppareilDetailComponent implements OnInit {
  appareil: IAppareil | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ appareil }) => (this.appareil = appareil));
  }

  previousState(): void {
    window.history.back();
  }
}
