import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HomeAutomationTestModule } from '../../../test.module';
import { AppareilComponent } from 'app/entities/appareil/appareil.component';
import { AppareilService } from 'app/entities/appareil/appareil.service';
import { Appareil } from 'app/shared/model/appareil.model';

describe('Component Tests', () => {
  describe('Appareil Management Component', () => {
    let comp: AppareilComponent;
    let fixture: ComponentFixture<AppareilComponent>;
    let service: AppareilService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HomeAutomationTestModule],
        declarations: [AppareilComponent]
      })
        .overrideTemplate(AppareilComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AppareilComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AppareilService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Appareil(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.appareils && comp.appareils[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
