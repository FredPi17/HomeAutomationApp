import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypeAppareil, TypeAppareil } from 'app/shared/model/type-appareil.model';
import { TypeAppareilService } from './type-appareil.service';

@Component({
  selector: 'jhi-type-appareil-update',
  templateUrl: './type-appareil-update.component.html'
})
export class TypeAppareilUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]]
  });

  constructor(protected typeAppareilService: TypeAppareilService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeAppareil }) => {
      this.updateForm(typeAppareil);
    });
  }

  updateForm(typeAppareil: ITypeAppareil): void {
    this.editForm.patchValue({
      id: typeAppareil.id,
      name: typeAppareil.name
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeAppareil = this.createFromForm();
    if (typeAppareil.id !== undefined) {
      this.subscribeToSaveResponse(this.typeAppareilService.update(typeAppareil));
    } else {
      this.subscribeToSaveResponse(this.typeAppareilService.create(typeAppareil));
    }
  }

  private createFromForm(): ITypeAppareil {
    return {
      ...new TypeAppareil(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeAppareil>>): void {
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
