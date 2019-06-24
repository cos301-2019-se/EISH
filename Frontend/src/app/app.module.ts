import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomepageComponent } from './pages/homepage/homepage.component';
import { SettingsComponent } from './pages/settings/settings.component';
import { LoginComponent } from './templates/forms/login/login.component';
import { ChangeCredentialsComponent } from './templates/forms/change-credentials/change-credentials.component';
import { KeysComponent } from './templates/forms/keys/keys.component';
import { HttpClientModule } from '@angular/common/http';
import {UserAccessControlService} from './services/user/user-access-control.service';
import {InputService} from './services/input/input.service';
import { MaterialsComponent } from './templates/materials/materials.component';


@NgModule({
  declarations: [
    AppComponent,
    HomepageComponent,
    SettingsComponent,
    LoginComponent,
    ChangeCredentialsComponent,
    KeysComponent,
    MaterialsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [UserAccessControlService, InputService],
  bootstrap: [AppComponent]
})
export class AppModule { }
