import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HomeAutomationSharedModule } from 'app/shared/shared.module';
import { AppareilComponent } from './appareil.component';
import { AppareilDetailComponent } from './appareil-detail.component';
import { AppareilUpdateComponent } from './appareil-update.component';
import { AppareilDeleteDialogComponent } from './appareil-delete-dialog.component';
import { appareilRoute } from './appareil.route';

@NgModule({
  imports: [HomeAutomationSharedModule, RouterModule.forChild(appareilRoute)],
  declarations: [AppareilComponent, AppareilDetailComponent, AppareilUpdateComponent, AppareilDeleteDialogComponent],
  entryComponents: [AppareilDeleteDialogComponent]
})
export class HomeAutomationAppareilModule {}
