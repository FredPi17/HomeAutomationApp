import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HomeAutomationTestModule } from '../../../test.module';
import { TypeAppareilDetailComponent } from 'app/entities/type-appareil/type-appareil-detail.component';
import { TypeAppareil } from 'app/shared/model/type-appareil.model';

describe('Component Tests', () => {
  describe('TypeAppareil Management Detail Component', () => {
    let comp: TypeAppareilDetailComponent;
    let fixture: ComponentFixture<TypeAppareilDetailComponent>;
    const route = ({ data: of({ typeAppareil: new TypeAppareil(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HomeAutomationTestModule],
        declarations: [TypeAppareilDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TypeAppareilDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypeAppareilDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typeAppareil on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typeAppareil).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
