import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITranscript } from '../transcript.model';
import { TranscriptService } from '../service/transcript.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './transcript-delete-dialog.component.html',
})
export class TranscriptDeleteDialogComponent {
  transcript?: ITranscript;

  constructor(protected transcriptService: TranscriptService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.transcriptService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
