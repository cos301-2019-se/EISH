import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './template/navbar/navbar.component';
import { GeneratorsComponent } from './pages/generators/generators.component';
import { DevicesComponent } from './pages/devices/devices.component';
import { HomeComponent } from './pages/home/home.component';
import { ModalComponent } from './template/modal/modal.component';
import { DeviceFormComponent } from './template/forms/device-form/device-form.component';
import { GeneratorFormComponent } from './template/forms/generator-form/generator-form.component';
import { DeviceViewComponent } from './template/modal/device-view/device-view.component';
import { HttpClientModule } from '@angular/common/http'; 
import {RequestsService  } from "./requests.service";
import { GoogleChartsModule } from 'angular-google-charts';

import { FormsModule }   from '@angular/forms';
import {GenModalComponent} from './template/modal/gen-modal/gen-modal.component';

import { ChartsComponent } from './charts/charts.component';
import { MaterialModule } from "./material";
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    GeneratorsComponent,
    DevicesComponent,
    HomeComponent,
    ModalComponent,
    DeviceFormComponent,
    GeneratorFormComponent,
    DeviceViewComponent,
    GenModalComponent,
    ChartsComponent
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    GoogleChartsModule, 
    FormsModule,
    MaterialModule, 
    BrowserAnimationsModule
  ],
  providers: [RequestsService],
  bootstrap: [AppComponent]
})
export class AppModule { }
