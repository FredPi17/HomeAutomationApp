import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { MqttConnectionService } from 'app/entities/mqtt-connection/mqtt-connection.service';
import { IMqttConnection, MqttConnection } from 'app/shared/model/mqtt-connection.model';

describe('Service Tests', () => {
  describe('MqttConnection Service', () => {
    let injector: TestBed;
    let service: MqttConnectionService;
    let httpMock: HttpTestingController;
    let elemDefault: IMqttConnection;
    let expectedResult: IMqttConnection | IMqttConnection[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(MqttConnectionService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new MqttConnection(0, 'AAAAAAA', 'AAAAAAA', 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a MqttConnection', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new MqttConnection()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a MqttConnection', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            urlServeur: 'BBBBBB',
            port: 1,
            username: 'BBBBBB',
            password: 'BBBBBB',
            topic: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of MqttConnection', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            urlServeur: 'BBBBBB',
            port: 1,
            username: 'BBBBBB',
            password: 'BBBBBB',
            topic: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a MqttConnection', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
