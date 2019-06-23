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
import { AccessControlInterceptor } from './services/interceptor/user-access-control.interceptor';
import { HTTP_INTERCEPTORS } from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent,
    HomepageComponent,
    SettingsComponent,
    LoginComponent,
    ChangeCredentialsComponent,
    KeysComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [UserAccessControlService, InputService, {
    provide: HTTP_INTERCEPTORS,
    useClass: AccessControlInterceptor,
    multi: true

  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
