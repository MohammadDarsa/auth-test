import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TranscriptComponent } from '../list/transcript.component';
import { TranscriptDetailComponent } from '../detail/transcript-detail.component';
import { TranscriptUpdateComponent } from '../update/transcript-update.component';
import { TranscriptRoutingResolveService } from './transcript-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const transcriptRoute: Routes = [
  {
    path: '',
    component: TranscriptComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TranscriptDetailComponent,
    resolve: {
      transcript: TranscriptRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TranscriptUpdateComponent,
    resolve: {
      transcript: TranscriptRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TranscriptUpdateComponent,
    resolve: {
      transcript: TranscriptRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(transcriptRoute)],
  exports: [RouterModule],
})
export class TranscriptRoutingModule {}
