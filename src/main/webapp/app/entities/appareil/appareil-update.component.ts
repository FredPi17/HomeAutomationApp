import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAppareil, Appareil } from 'app/shared/model/appareil.model';
import { AppareilService } from './appareil.service';

@Component({
  selector: 'jhi-appareil-update',
  templateUrl: './appareil-update.component.html'
})
export class AppareilUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    type: [],
    protocol: [],
    piece: []
  });

  constructor(protected appareilService: AppareilService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ appareil }) => {
      this.updateForm(appareil);
    });
  }

  updateForm(appareil: IAppareil): void {
    this.editForm.patchValue({
      id: appareil.id,
      name: appareil.name,
      type: appareil.type,
      protocol: appareil.protocol,
      piece: appareil.piece
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const appareil = this.createFromForm();
    if (appareil.id !== undefined) {
      this.subscribeToSaveResponse(this.appareilService.update(appareil));
    } else {
      this.subscribeToSaveResponse(this.appareilService.create(appareil));
    }
  }

  private createFromForm(): IAppareil {
    return {
      ...new Appareil(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      type: this.editForm.get(['type'])!.value,
      protocol: this.editForm.get(['protocol'])!.value,
      piece: this.editForm.get(['piece'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAppareil>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
