import { Component, OnInit } from '@angular/core';
import {MatDialogModule} from '@angular/material/dialog';
import {FormControl} from '@angular/forms';
import {Observable} from 'rxjs';
import {map, startWith} from 'rxjs/operators';


@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {
  panelOpenState = false;
  deviceFound: Boolean;
  userDeviceName = new FormControl();
  options: string[] = ['bedroom_light', 'Kettle', 'bedroom_fan','lounge_TV'];
  filteredOptions: Observable<string[]>;
  constructor() { }

  ngOnInit() {
    this.filteredOptions = this.userDeviceName.valueChanges
      .pipe(
        startWith(''),
        map(value => this._filter(value))
      );
  }
  
  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.options.filter(option => option.toLowerCase().includes(filterValue));
  }

  returnDevice(){
    this.deviceFound=true;
    let deviceResult = "Found";
  }
  removeUser(){

  }

  editUser(){

  }

  addDevice(){

  }

  editDevice(){

  }

  removeDevice(){
    
  }

  addPowerGenerator(){

  }

  editPowerGenerator(){

  }

  removePowerGenerator(){
    
  }
}
