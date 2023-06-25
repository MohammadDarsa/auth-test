import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITranscript, NewTranscript } from '../transcript.model';

export type PartialUpdateTranscript = Partial<ITranscript> & Pick<ITranscript, 'id'>;

type RestOf<T extends ITranscript | NewTranscript> = Omit<T, 'date'> & {
  date?: string | null;
};

export type RestTranscript = RestOf<ITranscript>;

export type NewRestTranscript = RestOf<NewTranscript>;

export type PartialUpdateRestTranscript = RestOf<PartialUpdateTranscript>;

export type EntityResponseType = HttpResponse<ITranscript>;
export type EntityArrayResponseType = HttpResponse<ITranscript[]>;

@Injectable({ providedIn: 'root' })
export class TranscriptService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/transcripts');
  protected resourceUrlStudent = this.applicationConfigService.getEndpointFor('api/transcripts/student');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(transcript: NewTranscript): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transcript);
    return this.http
      .post<RestTranscript>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(transcript: ITranscript): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transcript);
    return this.http
      .put<RestTranscript>(`${this.resourceUrl}/${this.getTranscriptIdentifier(transcript)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(transcript: PartialUpdateTranscript): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transcript);
    return this.http
      .patch<RestTranscript>(`${this.resourceUrl}/${this.getTranscriptIdentifier(transcript)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestTranscript>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestTranscript[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }
  public getTransForStudent(): Observable<EntityArrayResponseType> {
    return this.http.get<ITranscript[]>(`${this.resourceUrlStudent}`, { observe: 'response' });
  }
  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTranscriptIdentifier(transcript: Pick<ITranscript, 'id'>): number {
    return transcript.id;
  }

  compareTranscript(o1: Pick<ITranscript, 'id'> | null, o2: Pick<ITranscript, 'id'> | null): boolean {
    return o1 && o2 ? this.getTranscriptIdentifier(o1) === this.getTranscriptIdentifier(o2) : o1 === o2;
  }

  addTranscriptToCollectionIfMissing<Type extends Pick<ITranscript, 'id'>>(
    transcriptCollection: Type[],
    ...transcriptsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const transcripts: Type[] = transcriptsToCheck.filter(isPresent);
    if (transcripts.length > 0) {
      const transcriptCollectionIdentifiers = transcriptCollection.map(transcriptItem => this.getTranscriptIdentifier(transcriptItem)!);
      const transcriptsToAdd = transcripts.filter(transcriptItem => {
        const transcriptIdentifier = this.getTranscriptIdentifier(transcriptItem);
        if (transcriptCollectionIdentifiers.includes(transcriptIdentifier)) {
          return false;
        }
        transcriptCollectionIdentifiers.push(transcriptIdentifier);
        return true;
      });
      return [...transcriptsToAdd, ...transcriptCollection];
    }
    return transcriptCollection;
  }

  protected convertDateFromClient<T extends ITranscript | NewTranscript | PartialUpdateTranscript>(transcript: T): RestOf<T> {
    return {
      ...transcript,
      date: transcript.date?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restTranscript: RestTranscript): ITranscript {
    return {
      ...restTranscript,
      date: restTranscript.date ? dayjs(restTranscript.date) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestTranscript>): HttpResponse<ITranscript> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestTranscript[]>): HttpResponse<ITranscript[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
