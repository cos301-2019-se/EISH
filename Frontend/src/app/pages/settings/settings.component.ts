import { Component, OnInit } from '@angular/core';
import {MatDialogModule, MatDialogConfig} from '@angular/material/dialog';
import {FormControl} from '@angular/forms';
import {Observable} from 'rxjs';
import {map, startWith} from 'rxjs/operators';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import {SelectionModel} from '@angular/cdk/collections';
import  {MatTableDataSource} from '@angular/material/table';
import { DeviceModalComponent } from './device-modal/device-modal.component';
@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})

export class SettingsComponent implements OnInit {
  //for user table
  displayedColumns = ["select","name","email","expiryDate","resident"];
  dataSource = new MatTableDataSource<Users>(ELEMENT_DATA);    //set this upon retrieving data from DB
  selection = new SelectionModel<Users>(true,[]);
  formData: any;
  isGuest: Boolean;


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
  tableHeaders = ["Name","Email","Expiry Date","Resident","Remove"];
  editField: string;
    personList: Array<any> = [
      { userId: 1, userName: 'Aurelia Vega', userEmail:"auriela@gmail.com", userExpiryDate:"20/07/2019", userType:true},
      { userId: 2, userName: 'Guadalupe House', userEmail:"guapdad@gmail.com", userExpiryDate:"09/08/2019",userType:false},
      { userId: 3, userName: 'Austin Post', userEmail:"postedup@gmail.com", userExpiryDate:"11/07/2019", userType:false},
      { userId: 4,userName: 'Jacob Thompson', userEmail:"jakieboy@gmail.com", userExpiryDate:"20/07/2019", userType:false},
      { userId:5,userName:"John",userEmail:"john@gmail.com",userExpiryDate:"10/07/2019",userType:true},
      { userId:6,userName:"Matthew",userEmail:"matthew@gmail.com",userExpiryDate:"10/07/2019",userType:true},
      { userId:7,userName:"Andy",userEmail:"andy@gmail.com",userExpiryDate:"10/07/2019",userType:false},
      { userId:8,userName:"Marcus",userEmail:"marcus@gmail.com",userExpiryDate:"10/07/2019",userType:true}
    ];

    updateList(id: number, property: string, event: any) {
      if(property == "userType"){
        console.log("Before: "+this.personList[id][property]);
        this.personList[id][property] = !this.personList[id][property];
        //console.log("toggled resident");
        console.log("After: "+this.personList[id][property]);
      }else{
        const editField = event.target.textContent;
      this.personList[id][property] = editField;
      console.log(this.editField);
      }
    }

    remove(id: any) {
      this.personList.splice(id, 1);
    }

    changeValue(id: number, property: string, event: any) {
     if(property == "userType"){
      console.log(event.type);
     }else{
      this.editField = event.target.textContent;
      console.log(this.editField);
     }
    }
  constructor(private dialog: MatDialog) { }

  openDialog(){
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.disableClose = true;
    const dialogRef = this.dialog.open(DeviceModalComponent,dialogConfig);

    dialogRef.afterClosed().subscribe(
      data => {this.formData = data;}
    );
  }
  ngOnInit() {
    this.deviceFound=false;
    this.filteredOptions = this.userDeviceName.valueChanges
      .pipe(
        startWith(''),
        map(value => this._filter(value))
      );

  }
  
  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.options.filter(option => option.includes(filterValue));
  }

  returnDevice(){
    this.deviceFound=true;
    this.deviceResult = "{{display "+ this.userDeviceName.value+ " information}}";
    return;
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
  checkboxLabel(row?: Users): string{
    if(!row){
      return `${this.isAllSelected() ? 'select' : 'deselect'}all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect':'select'} row ${row.userId+1}`;
  }
  
  removeUser(index){
    //use spilce to delete user from current data set then send request to server to delete user
    let hold =this.dataSource.data;
    hold.splice(index,1);
    this.dataSource.data = hold;
  }

  editUser(){

  }
  update(){

  }
  addDevice( data){

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
  userId: number;
  userEmail: String;
  userExpiryDate: any;
  userType: Boolean; //coz either resident or guest (column will say resident) so false means guest and true means resident
}
const ELEMENT_DATA :Users[]=[
  {userId:1,userName:"John",userEmail:"john@gmail.com",userExpiryDate:"10/07/2019",userType:true},
  {userId:2,userName:"Matthew",userEmail:"matthew@gmail.com",userExpiryDate:"10/07/2019",userType:true},
  {userId:3,userName:"Andy",userEmail:"andy@gmail.com",userExpiryDate:"10/07/2019",userType:false},
  {userId:4,userName:"Marcus",userEmail:"marcus@gmail.com",userExpiryDate:"10/07/2019",userType:true}
];
//for devices table
export interface Devices{
  deviceName: String;
  deviceTopic: String;
  devicePriorityType: String;
}
