import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HomeAutomationTestModule } from '../../../test.module';
import { AppareilDetailComponent } from 'app/entities/appareil/appareil-detail.component';
import { Appareil } from 'app/shared/model/appareil.model';

describe('Component Tests', () => {
  describe('Appareil Management Detail Component', () => {
    let comp: AppareilDetailComponent;
    let fixture: ComponentFixture<AppareilDetailComponent>;
    const route = ({ data: of({ appareil: new Appareil(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HomeAutomationTestModule],
        declarations: [AppareilDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AppareilDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AppareilDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load appareil on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.appareil).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
