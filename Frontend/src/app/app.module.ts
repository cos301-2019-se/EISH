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

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    GeneratorsComponent,
    DevicesComponent,
    HomeComponent,
    ModalComponent,
    DeviceFormComponent,
    GeneratorFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
