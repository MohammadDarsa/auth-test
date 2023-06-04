import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITranscript } from '../transcript.model';

@Component({
  selector: 'jhi-transcript-detail',
  templateUrl: './transcript-detail.component.html',
})
export class TranscriptDetailComponent implements OnInit {
  transcript: ITranscript | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transcript }) => {
      this.transcript = transcript;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
