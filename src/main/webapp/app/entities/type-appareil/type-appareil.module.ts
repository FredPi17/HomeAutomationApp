import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HomeAutomationSharedModule } from 'app/shared/shared.module';
import { TypeAppareilComponent } from './type-appareil.component';
import { TypeAppareilDetailComponent } from './type-appareil-detail.component';
import { TypeAppareilUpdateComponent } from './type-appareil-update.component';
import { TypeAppareilDeleteDialogComponent } from './type-appareil-delete-dialog.component';
import { typeAppareilRoute } from './type-appareil.route';

@NgModule({
  imports: [HomeAutomationSharedModule, RouterModule.forChild(typeAppareilRoute)],
  declarations: [TypeAppareilComponent, TypeAppareilDetailComponent, TypeAppareilUpdateComponent, TypeAppareilDeleteDialogComponent],
  entryComponents: [TypeAppareilDeleteDialogComponent]
})
export class HomeAutomationTypeAppareilModule {}
