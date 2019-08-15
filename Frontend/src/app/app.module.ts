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
import { DevicesComponent } from './pages/devices/devices/devices.component';
import { NotificationComponent } from './pages/notifications/notification.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { ConsumptionComponent } from './pages/consumption/consumption.component';
import { ForecastComponent } from './pages/forecast/forecast.component';
import { BatteryChartComponent } from './pages/dashboard/battery-chart/battery-chart.component';
import { ConsumptionChartComponent } from './pages/consumption/consumption-chart/consumption-chart.component';
import { SideNavComponent } from './side-nav/side-nav.component';
import { LayoutModule } from '@angular/cdk/layout';
import { MatProgressBarModule, MatCardModule,
        MatSlideToggleModule, MatExpansionModule,
        MatAutocompleteModule, MatFormFieldModule,
        MatSelectModule, MatInputModule,
        MatCheckboxModule, MatDialogModule,
        MatTableModule, MatMenuModule,
        MatToolbarModule, MatButtonModule,
        MatSidenavModule, MatIconModule, MatPaginatorModule,
        MatListModule } from '@angular/material';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { FormsModule } from '@angular/forms';
import { DeviceModalComponent } from './pages/settings/device-modal/device-modal.component';
import 'hammerjs';
import { MDBBootstrapModule, WavesModule, InputsModule, ButtonsModule } from 'node_modules/angular-bootstrap-md';
import {DeviceService} from './services/devices/device.service';
import { DailyPlannerComponent } from './pages/daily-planner/daily-planner.component';
import { NgxGaugeModule } from 'node_modules/ngx-gauge';
import { InjectableRxStompConfig, RxStompService, rxStompServiceFactory } from '@stomp/ng2-stompjs';
import { eishmsRxStompConfig } from './services/stomp/rx-stomp.config';
import { GenerationComponent } from './pages/generation/generation.component';
import { GenerationChartComponent } from './pages/generation/generation-chart/generation-chart.component';
import { SingleDeviceComponent } from './pages/devices/single-device/single-device.component';
import { GeneratorModalComponent } from './templates/forms/generator-modal/generator-modal.component';
import { NotificationsComponent } from './pages/notifications/notifications.component';
import { NotifierModule, NotifierOptions } from 'angular-notifier';

const customNotifierOptions: NotifierOptions = {
  position: {
  horizontal: {
  position: 'right',
  distance: 12
  },
  vertical: {
  position: 'bottom',
  distance: 12,
  gap: 10
  }
  },
  theme: 'material',
  behaviour: {
    autoHide: 5000,
    onClick: 'hide',
    onMouseover: 'pauseAutoHide',
    showDismissButton: true,
    stacking: 4
  },
  animations: {
    enabled: true,
    show: {
      preset: 'slide',
      speed: 300,
      easing: 'ease'
    },
    hide: {
      preset: 'fade',
      speed: 300,
      easing: 'ease',
      offset: 50
    },
    shift: {
      speed: 300,
      easing: 'ease'
    },
    overlap: 150
  }
};

@NgModule({
  declarations: [
    AppComponent,
    SettingsComponent,
    LoginComponent,
    ChangeCredentialsComponent,
    KeysComponent,
    MaterialsComponent,
    DevicesComponent,
    NotificationComponent,
    DashboardComponent,
    ConsumptionComponent,
    ForecastComponent,
    BatteryChartComponent,
    ConsumptionChartComponent,
    SideNavComponent,
    DeviceModalComponent,
    DailyPlannerComponent,
    GenerationComponent,
    GenerationChartComponent,
    SingleDeviceComponent
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
    MatListModule,
    MatMenuModule,
    MatSlideToggleModule,
    MatExpansionModule,
    MatAutocompleteModule,
    MatFormFieldModule,
    FormsModule,
    MatInputModule,
    MatSelectModule,
    NotifierModule.withConfig(customNotifierOptions),
    MatTableModule,
    MatCheckboxModule, MatDialogModule,
    BrowserAnimationsModule,
    MDBBootstrapModule,
    MatCardModule,
    MatPaginatorModule,
    NgxGaugeModule,
    WavesModule, InputsModule, ButtonsModule,
    MatProgressBarModule
  ],
  exports: [
    BrowserAnimationsModule
    // SocketIoModule.forRoot(config)
  ],
  providers: [UserAccessControlService, InputService, DeviceService, {
    provide: HTTP_INTERCEPTORS,
    useClass: AccessControlInterceptor,
    multi: true

  }, {
    provide: InjectableRxStompConfig,
    useValue: eishmsRxStompConfig
  },
  {
    provide: RxStompService,
    useFactory: rxStompServiceFactory,
    deps: [InjectableRxStompConfig]
  }],
  bootstrap: [AppComponent],
  entryComponents: [DeviceModalComponent ]
})

export class AppModule {}
