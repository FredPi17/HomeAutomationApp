import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HomeAutomationTestModule } from '../../../test.module';
import { PieceComponent } from 'app/entities/piece/piece.component';
import { PieceService } from 'app/entities/piece/piece.service';
import { Piece } from 'app/shared/model/piece.model';

describe('Component Tests', () => {
  describe('Piece Management Component', () => {
    let comp: PieceComponent;
    let fixture: ComponentFixture<PieceComponent>;
    let service: PieceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HomeAutomationTestModule],
        declarations: [PieceComponent]
      })
        .overrideTemplate(PieceComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PieceComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PieceService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Piece(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pieces && comp.pieces[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
