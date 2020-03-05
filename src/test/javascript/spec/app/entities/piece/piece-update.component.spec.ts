import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HomeAutomationTestModule } from '../../../test.module';
import { PieceUpdateComponent } from 'app/entities/piece/piece-update.component';
import { PieceService } from 'app/entities/piece/piece.service';
import { Piece } from 'app/shared/model/piece.model';

describe('Component Tests', () => {
  describe('Piece Management Update Component', () => {
    let comp: PieceUpdateComponent;
    let fixture: ComponentFixture<PieceUpdateComponent>;
    let service: PieceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HomeAutomationTestModule],
        declarations: [PieceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PieceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PieceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PieceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Piece(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Piece();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
