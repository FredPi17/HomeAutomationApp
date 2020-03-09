import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HomeAutomationTestModule } from '../../../test.module';
import { PieceDetailComponent } from 'app/entities/piece/piece-detail.component';
import { Piece } from 'app/shared/model/piece.model';

describe('Component Tests', () => {
  describe('Piece Management Detail Component', () => {
    let comp: PieceDetailComponent;
    let fixture: ComponentFixture<PieceDetailComponent>;
    const route = ({ data: of({ piece: new Piece(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HomeAutomationTestModule],
        declarations: [PieceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PieceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PieceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load piece on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.piece).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
