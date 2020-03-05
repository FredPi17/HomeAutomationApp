import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypeAppareil } from 'app/shared/model/type-appareil.model';

type EntityResponseType = HttpResponse<ITypeAppareil>;
type EntityArrayResponseType = HttpResponse<ITypeAppareil[]>;

@Injectable({ providedIn: 'root' })
export class TypeAppareilService {
  public resourceUrl = SERVER_API_URL + 'api/type-appareils';

  constructor(protected http: HttpClient) {}

  create(typeAppareil: ITypeAppareil): Observable<EntityResponseType> {
    return this.http.post<ITypeAppareil>(this.resourceUrl, typeAppareil, { observe: 'response' });
  }

  update(typeAppareil: ITypeAppareil): Observable<EntityResponseType> {
    return this.http.put<ITypeAppareil>(this.resourceUrl, typeAppareil, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITypeAppareil>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITypeAppareil[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
