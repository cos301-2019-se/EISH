import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from './templates/forms/login/login.component';
import {ChangeCredentialsComponent} from './templates/forms/change-credentials/change-credentials.component';
import {KeysComponent} from './templates/forms/keys/keys.component';
import { SettingsComponent } from './pages/settings/settings.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { DevicesComponent } from './pages/devices/devices/devices.component';
import { ConsumptionComponent } from './pages/consumption/consumption.component';
import { ForecastComponent } from './pages/forecast/forecast.component';
import { SideNavComponent } from './side-nav/side-nav.component';
import { GenerationComponent } from './pages/generation/generation.component';
import { UserAccessControlService } from './services/user/user-access-control.service';
import { NotificationsComponent } from './pages/notifications/notifications.component';

const routes: Routes = [
  { path: '', component: LoginComponent},
  {path: 'homepage', component: SideNavComponent, children: [
    {path: 'dashboard', component: DashboardComponent},
    {path: 'settings', component: SettingsComponent, canActivate: [ UserAccessControlService ]},
    {path: 'devices', component: DevicesComponent},
    {path: 'consumption', component: ConsumptionComponent},
    {path: 'forecast', component: ForecastComponent},
    {path: 'generation', component: GenerationComponent},
    {path: 'notification', component: NotificationsComponent}
  ]},
  {path: 'register/:regType', component: ChangeCredentialsComponent},
  {path: 'key', component: KeysComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [UserAccessControlService]
})
export class AppRoutingModule { }
