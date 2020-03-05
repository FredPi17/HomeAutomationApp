import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeAppareil } from 'app/shared/model/type-appareil.model';

@Component({
  selector: 'jhi-type-appareil-detail',
  templateUrl: './type-appareil-detail.component.html'
})
export class TypeAppareilDetailComponent implements OnInit {
  typeAppareil: ITypeAppareil | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeAppareil }) => (this.typeAppareil = typeAppareil));
  }

  previousState(): void {
    window.history.back();
  }
}
