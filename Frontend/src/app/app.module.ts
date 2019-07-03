import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule} from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SettingsComponent } from './pages/settings/settings.component';
import { LoginComponent } from './templates/forms/login/login.component';
import { ChangeCredentialsComponent } from './templates/forms/change-credentials/change-credentials.component';
import { KeysComponent } from './templates/forms/keys/keys.component';
import { HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http';
import {UserAccessControlService} from './services/user/user-access-control.service';
import {InputService} from './services/input/input.service';
import { MaterialsComponent } from './templates/materials/materials.component';
import { AccessControlInterceptor } from './services/interceptor/user-access-control.interceptor';
import { AllDevicesComponent } from './pages/devices/all_devices/all-devices.component';
import { DevicesComponent } from './pages/devices/devices/devices.component';
import { NotificationComponent } from './pages/notifications/notification.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { ConsumptionComponent } from './pages/consumption/consumption.component';
import { ForecastComponent } from './pages/forecast/forecast.component';
import { BatteryChartComponent } from './pages/dashboard/battery-chart/battery-chart.component';
import { ConsumptionChartComponent } from './pages/consumption/consumption-chart/consumption-chart.component';
import { SideNavComponent } from './side-nav/side-nav.component';
import { LayoutModule } from '@angular/cdk/layout';
<<<<<<< Updated upstream
import { MatToolbarModule, MatButtonModule, MatSidenavModule, MatIconModule, MatListModule } from '@angular/material';

=======
import { MatMenuModule,MatToolbarModule, MatButtonModule, MatSidenavModule, MatIconModule, MatListModule } from '@angular/material';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {MatFormFieldModule} from '@angular/material/form-field';
import { FormsModule } from '@angular/forms';
import {MatInputModule} from '@angular/material/input';
>>>>>>> Stashed changes

@NgModule({
  declarations: [
    AppComponent,
    SettingsComponent,
    LoginComponent,
    ChangeCredentialsComponent,
    KeysComponent,
    MaterialsComponent,
    AllDevicesComponent,
    DevicesComponent,
    NotificationComponent,
    DashboardComponent,
    ConsumptionComponent,
    ForecastComponent,
    BatteryChartComponent,
    ConsumptionChartComponent,
<<<<<<< Updated upstream
    SideNavComponent, 
    //AccessControlInterceptor
=======
    SideNavComponent
>>>>>>> Stashed changes
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    LayoutModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
<<<<<<< Updated upstream
    MatListModule
=======
    MatListModule,
    MatMenuModule,
    MatExpansionModule,
    MatAutocompleteModule,
    MatFormFieldModule,
    FormsModule,
    MatInputModule,
    BrowserAnimationsModule
  ],
  exports:[
    BrowserAnimationsModule
>>>>>>> Stashed changes
  ],
  providers: [UserAccessControlService, InputService, {
    provide: HTTP_INTERCEPTORS,
    useClass: AccessControlInterceptor,
    multi: true

  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
