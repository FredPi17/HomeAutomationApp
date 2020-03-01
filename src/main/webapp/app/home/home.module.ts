import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HomeAutomationSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';

@NgModule({
  imports: [HomeAutomationSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent]
})
export class HomeAutomationHomeModule {}
