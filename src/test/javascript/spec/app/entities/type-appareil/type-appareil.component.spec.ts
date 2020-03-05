import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HomeAutomationTestModule } from '../../../test.module';
import { TypeAppareilComponent } from 'app/entities/type-appareil/type-appareil.component';
import { TypeAppareilService } from 'app/entities/type-appareil/type-appareil.service';
import { TypeAppareil } from 'app/shared/model/type-appareil.model';

describe('Component Tests', () => {
  describe('TypeAppareil Management Component', () => {
    let comp: TypeAppareilComponent;
    let fixture: ComponentFixture<TypeAppareilComponent>;
    let service: TypeAppareilService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HomeAutomationTestModule],
        declarations: [TypeAppareilComponent]
      })
        .overrideTemplate(TypeAppareilComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeAppareilComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeAppareilService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TypeAppareil(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.typeAppareils && comp.typeAppareils[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
