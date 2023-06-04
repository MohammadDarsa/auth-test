import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TranscriptComponent } from './list/transcript.component';
import { TranscriptDetailComponent } from './detail/transcript-detail.component';
import { TranscriptUpdateComponent } from './update/transcript-update.component';
import { TranscriptDeleteDialogComponent } from './delete/transcript-delete-dialog.component';
import { TranscriptRoutingModule } from './route/transcript-routing.module';

@NgModule({
  imports: [SharedModule, TranscriptRoutingModule],
  declarations: [TranscriptComponent, TranscriptDetailComponent, TranscriptUpdateComponent, TranscriptDeleteDialogComponent],
})
export class TranscriptModule {}
