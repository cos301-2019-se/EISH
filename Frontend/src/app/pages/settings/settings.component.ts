import { Component, OnInit } from '@angular/core';
import {MatDialogModule} from '@angular/material/dialog';
import {FormControl} from '@angular/forms';
import {Observable} from 'rxjs';
import {map, startWith} from 'rxjs/operators';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import {SelectionModel} from '@angular/cdk/collections';
import  {MatTableDataSource} from '@angular/material/table';
@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})

export class SettingsComponent implements OnInit {
  //for user table
  displayedColumns = ["select","position","name","weight","symbol"];
  dataSource = new MatTableDataSource<Elements>(ELEMENT_DATA);    //set this upon retrieving data from DB
  selection = new SelectionModel<Elements>(true,[]);

  //for search bar
  panelOpenState = false;
  deviceFound: Boolean;
  userDeviceName = new FormControl();
  options: string[] = ['bedroom_light', 'Kettle', 'bedroom_fan','lounge_TV'];
  filteredOptions: Observable<string[]>;

  ///for devices
  deviceName: String;
  deviceTopic: String;
  maxWatt: number;
  minWatt: number;
  priority: string[] = ['Nice-to-have', 'Always-on','Must-have'];
  deviceResult: string;

  constructor() { }

  ngOnInit() {
    this.deviceFound=false;
    this.filteredOptions = this.userDeviceName.valueChanges
      .pipe(
        startWith(''),
        map(value => this._filter(value))
      );

    
  }
  
  isAllSelected(){
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }
  masterToggle(){
    this.isAllSelected()?
      this.selection.clear():
      this.dataSource.data.forEach(row=>this.selection.select(row));
  }
  checkboxLabel(row?: Elements): string{
    if(!row){
      return `${this.isAllSelected() ? 'select' : 'deselect'}all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect':'select'} row ${row.position+1}`;
  }
  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.options.filter(option => option.toLowerCase().includes(filterValue));
  }

  returnDevice(){
    this.deviceFound=true;
    this.deviceResult = "{{display "+ this.userDeviceName.value+ " information}}";
    return;
  }
  removeUser(){
    //use spilce to delete user from current data set then send request to server to delete user
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

//for users table
export interface Users{
  userName: String;
  userEmail: String;
  userExpiryDate: any;
  userType: Boolean; //coz either resident or guest (column will say resident) so false means guest and true means resident
}

//for devices table
export interface Devices{
  deviceName: String;
  deviceTopic: String;
  devicePriorityType: String;
}

export interface Elements{
  position:number;
  name:string;
  weight:number;
  symbol:string;

}

const ELEMENT_DATA : Elements[]=[
  {position:1,name:'Hydrogen',weight:1.0079,symbol:'H'},
  {position:2,name:'Helium',weight:4.0026,symbol:'He'},
  {position:3,name:'Lithium',weight:6.941,symbol:'Li'},
  {position:4,name:'Berrylium',weight:9.0122,symbol:'Be'},
  {position:5,name:'Boron',weight:10.811,symbol:'B'},
  {position:6,name:'Carbon',weight:12.0107,symbol:'C'}
];