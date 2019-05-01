import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DevicesComponent } from './pages/devices/devices.component';
import { GeneratorsComponent } from './pages/generators/generators.component';
import { HomeComponent } from './pages/home/home.component';

const routes: Routes = [
  {path: '', component:HomeComponent},
  {path: 'devices', component:DevicesComponent},
  {path: 'generators', component:GeneratorsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
