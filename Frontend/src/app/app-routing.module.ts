import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from './templates/forms/login/login.component';
import {ChangeCredentialsComponent} from './templates/forms/change-credentials/change-credentials.component';
import {KeysComponent} from './templates/forms/keys/keys.component';
import { SettingsComponent } from './pages/settings/settings.component';

const routes: Routes = [
  { path: '', component:LoginComponent},
  {path: 'register/:regType', component:ChangeCredentialsComponent},
  {path: 'key', component:KeysComponent},
  {path: 'settings',component:SettingsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
