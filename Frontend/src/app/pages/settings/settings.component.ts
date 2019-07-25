import { Component, OnInit } from '@angular/core';
import {MatDialogModule, MatDialogConfig} from '@angular/material/dialog';
import {FormControl} from '@angular/forms';
import {Observable} from 'rxjs';
import {map, startWith, catchError} from 'rxjs/operators';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import { DeviceModalComponent } from './device-modal/device-modal.component';
import { UserAccessControlService } from 'src/app/services/user/user-access-control.service';
import { GeneratorService } from 'src/app/services/generators/generator.service';
import { DeviceService } from 'src/app/services/devices/device.service';
import { Response } from 'selenium-webdriver/http';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})

export class SettingsComponent implements OnInit {
  constructor(private generatorService: GeneratorService,
              private userService: UserAccessControlService,
              private deviceService: DeviceService,
              private dialog: MatDialog) { }

  // for user table
  formData: any;
  isDataAvailable: boolean;
  userRemoveFailed: boolean;
  userChangeFailed: boolean;
  // for search bar
  panelOpenState = false;
  deviceFound: boolean;
  userDeviceName = new FormControl();
  filteredOptions: Observable<string[]>;
  // for devices
  deviceName: string;
  deviceTopic: string;
  maxWatt: number;
  minWatt: number;
  devicePriority: string;
  deviceResult: any;
  deviceTableHeaders = ['Device Name ', 'Device Topic', 'Device Priority' ];
  userTableHeaders = ['Name', 'Email', 'Current Expiry Date', 'Extend Expiry Date', 'Resident', 'Remove'];
  editField: string;
  deviceNames: string[];
  deviceRemoveFailed: boolean;
  deviceChangeFailed: boolean;
  deviceArray: any;
  userArray: any;

  ngOnInit() {
    this.deviceService.getAllDevices().pipe(
      map( response => {
          this.deviceArray =  response,
          JSON.stringify(this.deviceArray);

          let devices: string[];
          devices = [];
          for (let index = 0; index < this.deviceArray.length; index++) {
              devices[index] = this.deviceArray[index].deviceName;
            }
          this.deviceNames = devices;

          this.filteredOptions = this.userDeviceName.valueChanges
            .pipe(
              startWith(''),
              map(value => this._filter(value))

            );

          })
     ).subscribe();
    this.getUserList();
    this.deviceFound = false;
    this.userRemoveFailed = false;
    this.userChangeFailed = false;
    this.deviceRemoveFailed = false;
    this.deviceChangeFailed = false;
  }

  getDeviceList() {
    this.deviceService.getAllDevices().pipe(
      map( response => {
          this.deviceArray =  response,
          JSON.stringify(this.deviceArray);

          let devices: string[];
          devices = [];
          for (let index = 0; index < this.deviceArray.length; index++) {
              devices[index] = this.deviceArray[index].deviceName;
            }
          this.deviceNames = devices;

          this.filteredOptions = this.userDeviceName.valueChanges
            .pipe(
              startWith(''),
              map(value => this._filter(value))

            );

          })
     ).subscribe();
  }

    updateUserList(id: number, property: string, event: any) {
      if (property === 'userType') {
        console.log(this.userArray[id].userName);
        if (this.userArray[id].userType === 'ROLE_RESIDENT') {
          console.log('i am a ')
          const userUpdate = {
            userId: this.userArray[id].userId ,
            userType:  "ROLE_GUEST"
           };
          this.userService.changeUserType(userUpdate);

        } else {
          const userUpdate = {
            userId: this.userArray[id].userId ,
            userType:  "ROLE_RESIDENT"

           };
          this.userService.changeUserType(userUpdate);
          this.getUserList();
        }

      } else {
        const editField = event.target.textContent;

        // console.log(this.editField);
        const userUpdate = {
          userId: this.userArray[id].userId,
          nrDays: editField
        };
        this.userService.changeUserExpiration(userUpdate);
        this.getUserList();
      }
    }


    updateDeviceList(id: number, property: string, event: any) {
      if (property === 'deviceName') {

          console.log('in changing device name');
          const editField = event.target.textContent;
          console.log('edit field: ' + editField);
          const deviceUpdate = {
          deviceId: this.deviceResult[id].deviceId,
          deviceName: editField,
          deviceTopic: this.deviceResult[id].deviceTopic,
          devicePriorityType: this.deviceResult[id].devicePriority,
          // tslint:disable-next-line: quotemark
          deviceStates: ["ON", "OFF", "OFFLINE"]
          };

          console.log('object: ' + JSON.stringify(deviceUpdate));
          this.deviceService.editDevice(deviceUpdate).subscribe(
            ( res: Response) => {
              if (res.status !== 200) {
                this.deviceChangeFailed = true;
              }
           });

      } else if (property === 'deviceTopic') {
          console.log('in changing device topic');

          const editField = event.target.textContent;
          const deviceUpdate = {
          deviceId: this.deviceResult[id].deviceId,
          deviceName: this.deviceResult[id].deviceName,
          deviceTopic: editField,
          devicePriorityType: this.deviceResult[id].devicePriority,
          // tslint:disable-next-line: quotemark
          deviceStates: ["ON", "OFF", "OFFLINE"]
          };

          console.log('object: ' + JSON.stringify(deviceUpdate));
          this.deviceService.editDevice(deviceUpdate).subscribe(
            ( res: Response) => {
              if (res.status !== 200) {
                this.deviceChangeFailed = true;
              }
           });

      } else {
          console.log('changing device priortity');

          console.log('priority type change: ' + this.devicePriority);
          console.log(this.deviceResult[id]);
          const deviceUpdate = {
          deviceId: this.deviceResult[id].deviceId,
          deviceName: this.deviceResult[id].deviceName,
          deviceTopic: this.deviceResult[id].deviceTopic,
          devicePriorityType: this.devicePriority,
          // tslint:disable-next-line: quotemark
          deviceStates: ["ON", "OFF", "OFFLINE"]
          };
          console.log('object: ' + JSON.stringify(deviceUpdate));
          this.deviceService.editDevice(deviceUpdate).subscribe(
            ( res: Response) => {
              if (res.status !== 200) {
                this.deviceChangeFailed = true;
              }
           });
      }
    }

    resolveUserType(userType) {
      if (userType === 'ROLE_RESIDENT') {
        return true;
      } else {
        return false;
        
      }
    }

    removeUser(id: any) {
      this.userRemoveFailed = false;
      this.userService.removeUser(id).subscribe();
      this.userArray.splice(id, 1);
      return;
    }

    changeValue(id: number, property: string, event: any) {
      this.editField = event.target.textContent;

    }

    changeSelectValue(priority: string) {
      console.log('priority selected: ' + priority);
      this.devicePriority = priority;
    }

  openDialog() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.disableClose = true;
    dialogConfig.width = '400px';

    const dialogRef = this.dialog.open(DeviceModalComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(
      data => {this.formData = data; }
    );
  }


  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.deviceNames.filter(option => option.toLowerCase().includes(filterValue));
  }

  returnDevice() {

    if (this.userDeviceName.value === null || this.userDeviceName.value == null) {
      return;
    }

    const option = this.userDeviceName.value.toLowerCase();
    this.deviceResult = null;
    this.deviceResult = [];
    this.deviceFound = false;
    let arrayIndex = 0;
    // tslint:disable-next-line: prefer-for-of
    for (let index = 0; index < this.deviceArray.length; index++) {
        if (this.deviceArray[index].deviceName.toLowerCase().includes(option) ) {
          this.deviceFound = true;
          this.deviceResult[arrayIndex] = this.deviceArray[index];
          arrayIndex++;
        }
      }
    JSON.stringify(this.deviceResult);

    console.log(this.deviceResult);

      // JSON.parse(this.deviceResult)

      /* WHAT HAPPENS IF NOT FOUND
      if(this.deviceResult == ''){
        console.log('Not Found')
        this.deviceResult = JSON.stringify("{Not Found}")
    }*/

    return;
  }

  getUserList() {
    console.log('getting user list');
    this.userService.getAllUsers().pipe(
        map( response => {
            this.userArray =  response,
            JSON.stringify(this.userArray);
            console.log(this.userArray);
            this.isDataAvailable = true;
        })
    ).subscribe();
  }

  /**
   * Changes guest users' expiry date
   */
  editUserExpiry(userForm) {
    this.userService.changeUserExpiration(userForm);
  }

  /**
   * Changes users Type between Guest and Resident
   */
  editUserType(userForm) {
    this.userService.changeUserType(userForm.value);
  }

  /**
   * Edits device propertities
   */
  editDevice(deviceForm) {
    this.deviceService.editDevice(deviceForm.value);
  }

  /**
   * Removes a device from the system
   */
  removeDevice(deviceId) {
    let deviceObject: any;
    let arrayIndex: number;
    // tslint:disable-next-line: prefer-for-of
    for (let index = 0; index < this.deviceResult.length; index++) {
      if (this.deviceResult[index].deviceId === deviceId) {
        deviceObject = this.deviceResult[index];
        this.deviceResult.splice(index, 1);
        arrayIndex = this.deviceArray.indexOf(deviceObject);
        this.deviceArray.splice(arrayIndex, 1);

        arrayIndex = this.deviceNames.indexOf(deviceObject);
        this.deviceNames.splice(arrayIndex, 1);
        break;
      }
    }
    console.log('string: ' + this.userDeviceName.value);
    console.log('removing device with ID: ' + deviceId);
    this.deviceRemoveFailed =  false;
    this.deviceService.removeDevice(deviceId).subscribe(
      (res: Response) => {
          if (res.status === 200) {
            console.log('remove successful');
            let index = this.deviceArray
            this.deviceArray.splice(deviceId, 1);
            this.deviceResult.splice(deviceId, 1);
            this.getDeviceList();
            this.deviceRemoveFailed = true;
          } else {
            this.deviceRemoveFailed = false; }
      });
  }

  /**
   * Add new power generator to system
   */
  addPowerGenerator(genForm) {
    this.generatorService.addPowerGenerator(genForm.value);
  }

  /**
   * Edit a power generators' properties
   */
  editPowerGenerator(genForm) {
    this.generatorService.editPowerGenerator(genForm.value);
  }

  /**
   * Remove power generator from system
   */
  removePowerGenerator(generatorId) {
    this.generatorService.removePowerGenerator(generatorId);
  }
}
