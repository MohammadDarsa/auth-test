import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITranscript } from '../transcript.model';
import { TranscriptService } from '../service/transcript.service';

@Injectable({ providedIn: 'root' })
export class TranscriptRoutingResolveService implements Resolve<ITranscript | null> {
  constructor(protected service: TranscriptService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITranscript | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((transcript: HttpResponse<ITranscript>) => {
          if (transcript.body) {
            return of(transcript.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
