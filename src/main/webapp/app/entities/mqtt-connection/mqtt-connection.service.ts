import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMqttConnection } from 'app/shared/model/mqtt-connection.model';

type EntityResponseType = HttpResponse<IMqttConnection>;
type EntityArrayResponseType = HttpResponse<IMqttConnection[]>;

@Injectable({ providedIn: 'root' })
export class MqttConnectionService {
  public resourceUrl = SERVER_API_URL + 'api/mqtt-connections';

  constructor(protected http: HttpClient) {}

  create(mqttConnection: IMqttConnection): Observable<EntityResponseType> {
    return this.http.post<IMqttConnection>(this.resourceUrl, mqttConnection, { observe: 'response' });
  }

  update(mqttConnection: IMqttConnection): Observable<EntityResponseType> {
    return this.http.put<IMqttConnection>(this.resourceUrl, mqttConnection, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMqttConnection>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMqttConnection[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
