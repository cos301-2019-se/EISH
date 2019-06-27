import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from './templates/forms/login/login.component';
import {ChangeCredentialsComponent} from './templates/forms/change-credentials/change-credentials.component';
import {KeysComponent} from './templates/forms/keys/keys.component';

const routes: Routes = [
  /*{path: '',
  redirectTo: 'login',
  pathMatch: 'full'
  },*/
  { path: '', component:LoginComponent},
  {path: 'register?regType=Change', component:ChangeCredentialsComponent},
  {path: 'key', component:KeysComponent},
  {path: 'register?regType=Register', component: ChangeCredentialsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
