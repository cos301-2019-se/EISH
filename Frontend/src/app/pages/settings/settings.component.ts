import { Component, OnInit } from '@angular/core';
import { MatDialogConfig} from '@angular/material/dialog';
import {FormControl} from '@angular/forms';
import {Observable} from 'rxjs';
import {map, startWith} from 'rxjs/operators';
import {MatDialog} from '@angular/material';
import { DeviceModalComponent } from 'src/app/templates/forms/device-modal/device-modal.component';
import { GeneratorModalComponent } from 'src/app/templates/forms/generator-modal/generator-modal.component';
import { UserAccessControlService } from 'src/app/services/user/user-access-control.service';
import { GeneratorService } from 'src/app/services/generators/generator.service';
import { DeviceService } from 'src/app/services/devices/device.service';
import { Response } from 'selenium-webdriver/http';
import { NotifierService } from 'angular-notifier';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})

export class SettingsComponent implements OnInit {
  /**
   * Variables:
   */

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
  userTableHeaders = ['Name', 'Email', 'Days Until Expiry', 'Extend Expiry Date Days', 'Resident', 'Remove'];
  generatorTableHeaders = ['Generator Name ', 'Generator URL', 'Generator Priority' ];
  editField: string;
  deviceNames: string[];
  deviceRemoveFailed: boolean;
  deviceChangeFailed: boolean;
  deviceArray: any;
  userArray: any;

  //  for generators
  generatorArray: any;

  // for notifications
  private notifier: NotifierService;
  generatorChangeFailed: boolean;
  generatorPriority: any;

  /**
   * Functions:
   */

  /**
   * Default constuctor
   */
  constructor(private generatorService: GeneratorService,
              private userService: UserAccessControlService,
              private deviceService: DeviceService,
              private dialog: MatDialog,
              private notifierService: NotifierService) { }
  /**
   * default function executed on intialisation of page
   */
  ngOnInit() {
    this.getDeviceList();
    this.getAllGenerators();
    this.getUserList();
    this.deviceFound = false;
    this.userRemoveFailed = false;
    this.userChangeFailed = false;
    this.deviceRemoveFailed = false;
    this.deviceChangeFailed = false;

    this.notifier = this.notifierService;
    this.notifier.notify( 'success', 'You are awesome! I mean it!' );
    this.notifier.notify( 'warning', 'What it do babbbbyyyyyyy!!!' );
    this.notifier.notify( 'error', 'Damn!' );


  }

  // Device Functions //
  /**
   * Retrieves list of system devices
   */
  getDeviceList() { // getDeviceJSONArray
    console.log('getting device list');
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

  /**
   * Helper function
   * Stores selected value from device select list
   * @param priority: selected priority from list
   */
  changeSelectValue(priority: string) {
      console.log('priority selected: ' + priority);
      this.devicePriority = priority;
  }

  /**
   * Helper function
   * Stores selected value from generator select list
   * @param priority: selected priority from list
   */
  changeGeneratorSelectValue(priority: string) {
    console.log('priority selected: ' + priority);
    this.generatorPriority = priority;
}

  /**
   * Update device properties
   * @param id: device id
   * @param property: device property being changed
   * @param event: click | key press
   */
  updateDeviceList(id: number, property: string, event: any) {
    // change device name
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
        // changing device topic

        const editField = event.target.textContent;
        const deviceUpdate = {
          deviceId: this.deviceResult[id].deviceId,
          deviceName: this.deviceResult[id].deviceName,
          deviceTopic: editField,
          devicePriorityType: this.deviceResult[id].devicePriority,
          // tslint:disable-next-line: quotemark
          deviceStates: ["ON", "OFF", "OFFLINE"]
        };

        this.deviceService.editDevice(deviceUpdate).subscribe(
          ( res: Response) => {
              if (res.status !== 200) {
                this.deviceChangeFailed = true;
              }
           });

      } else {
          // changing device priortity

          const deviceUpdate = {
          deviceId: this.deviceResult[id].deviceId,
          deviceName: this.deviceResult[id].deviceName,
          deviceTopic: this.deviceResult[id].deviceTopic,
          devicePriorityType: this.devicePriority,
          // tslint:disable-next-line: quotemark
          deviceStates: ["ON", "OFF", "OFFLINE"]
          };
          // console.log('object: ' + JSON.stringify(deviceUpdate));
          this.deviceService.editDevice(deviceUpdate).subscribe(
            ( res: Response) => {
              if (res.status !== 200) {
                this.deviceChangeFailed = true;
              }
           });
      }
  }

  /**
   * Update generator properties
   * @param id: generator id
   * @param property: generator property being changed
   * @param event: click | key press
   */
  updateGeneratorList(id: number, property: string, event: any) {
    // change generator name
    if (property === 'generatorName') {
      console.log('in changing generator name');
      const editField = event.target.textContent;
      console.log('edit field: ' + editField);
      const generatorUpdate = {
      generatorId: this.generatorArray[id].generatorId,
      generatorName: editField,
      generatorUrl: this.generatorArray[id].generatorURL,
      // tslint:disable-next-line: quotemark
      generatorStates: ["ONLINE", "OFFLINE"]
    };

      console.log('object: ' + JSON.stringify(generatorUpdate));
      this.generatorService.editPowerGenerator(generatorUpdate).subscribe(
        ( res: Response) => {
          if (res.status !== 200) {
            this.generatorChangeFailed = true;
          }
       });

    } else if (property === 'generatorURL') {
        // changing generator URL

        const editField = event.target.textContent;
        const generatorUpdate = {
          generatorId: this.generatorArray[id].generatorId,
          generatorName: this.generatorArray[id].generatorName,
          generatorUrl: editField,
          // tslint:disable-next-line: quotemark
          generatorStates: ["ONLINE", "OFFLINE"]
        };

        console.log('object: ' + JSON.stringify(generatorUpdate));
        this.generatorService.editPowerGenerator(generatorUpdate).subscribe(
          ( res: Response) => {
            if (res.status !== 200) {
              this.generatorChangeFailed = true;
            }
         });

      } else {
          // changing generator priortity

          const generatorUpdate = {
            generatorId: this.generatorArray[id].generatorId,
            generatorName: this.generatorArray[id].generatorName,
            generatorUrl: this.generatorArray[id].generatorURL,
            generatorPriorityType: this.generatorPriority,
            // tslint:disable-next-line: quotemark
            generatorStates: ["ONLINE", "OFFLINE"]
          };

          console.log('object: ' + JSON.stringify(generatorUpdate));
          this.generatorService.editPowerGenerator(generatorUpdate).subscribe(
            ( res: Response) => {
              if (res.status !== 200) {
                this.generatorChangeFailed = true;
              }
           });
      }
  }

  /**
   * Returns all details of searched / selected device
   */
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
      /* WHAT HAPPENS IF NOT FOUND
      if(this.deviceResult == ''){
        console.log('Not Found')
        this.deviceResult = JSON.stringify("{Not Found}")
    }*/

    return;
  }

  /**
   * Removes a device from the system
   * @param deviceId;
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
            const index = this.deviceArray;
            this.deviceArray.splice(deviceId, 1);
            this.deviceResult.splice(deviceId, 1);
            this.getDeviceList();
            this.deviceRemoveFailed = true;
          } else {
            this.deviceRemoveFailed = false; }
      });
  }

  /**
   * Retrieves list of all system users
   */
  getUserList() {
    console.log('getting user list'); // getAllUsers || getUserJSONArray
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
   * change user details
   * @param id: table id
   * @param property: user property being changed
   * @param event: click | key press
   */
  updateUserList(id: number, property: string, event: any) {
      // change users' type:
      if (property === 'userType') {
        console.log(this.userArray[id].userName);
        if (this.userArray[id].userType === 'ROLE_RESIDENT') {
          const userUpdate = {
            userId: this.userArray[id].userId ,
            userType:  'ROLE_GUEST'
           };
          this.userService.changeUserType(userUpdate);

        } else {
          const userUpdate = {
            userId: this.userArray[id].userId ,
            userType:  'ROLE_RESIDENT'

           };
          this.userService.changeUserType(userUpdate);
          this.getUserList();
        }

      } else {
        // change users' expiration:
        const editField = event.target.textContent;
        const userUpdate = {
          userId: this.userArray[id].userId,
          nrDays: editField
        };
        this.userService.changeUserExpiration(userUpdate);
        this.getUserList();
      }
    }

  /**
   * Find user type of user within the array
   * @param userType: user role
   * @returns Boolean
   */
  resolveUserType(userType) {
    if (userType === 'ROLE_RESIDENT') {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Remove user from system
   * @param id: users' id
   */
  removeUser(id: any) {
      this.userRemoveFailed = false;
      this.userService.removeUser(id).subscribe();
      this.userArray.splice(id, 1);
      return;
  }

  // Generator Functions
  /**
   * Get all generators of the system
   */
  getAllGenerators() {
    this.generatorService.getGeneratorJSONArray().pipe(
      map( response => {
          this.generatorArray =  response,
          JSON.stringify(this.generatorArray);
          console.log(JSON.stringify(this.generatorArray));
        })
      ).subscribe();
  }
  /**
   * Add new power generator to system
   */
  addPowerGenerator(genForm) {

    this.generatorService.addPowerGenerator(genForm.value);
  }


  /**
   * Remove power generator from system
   */
  removePowerGenerator(generatorId) {
    this.generatorService.removePowerGenerator(generatorId);
  }

  // Additional Helper Functions
  /**
   * Helper function
   * Function used when key up event is triggered
   * @param id: table index
   * @param property;
   * @param event: key up
   */
    changeValue(id: number, property: string, event: any) {
      this.editField = event.target.textContent;

    }

    /**
     * Helper function
     * Opens modal for device or generator form
     * @param modalName: name of which modal to open
     */
    openDialog(modalName) {

      const dialogConfig = new MatDialogConfig();
      dialogConfig.autoFocus = true;
      dialogConfig.disableClose = true;
      dialogConfig.width = '400px';

      let dialogRef;
      if (modalName === 'DeviceModalComponent') {
        dialogRef = this.dialog.open(DeviceModalComponent, dialogConfig);
      } else {
        dialogRef = this.dialog.open(GeneratorModalComponent, dialogConfig);
      }

      // Does this work ??? What does it do?
      dialogRef.afterClosed().subscribe(
        data => {this.formData = data; }
      );
  }

  /**
   * Helper function
   * Filters device array during search
   * @param value: string searched for in array
   */
  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.deviceNames.filter(option => option.toLowerCase().includes(filterValue));
  }

  daysToExpiry(userId) {
      const time = new Date(this.userArray[userId].userExpiryDate).getTime() - new Date().getTime();
      const days = Math.floor(time / (1000 * 60 * 60 * 24));
      return  days;
  }

  discoverHomeLocation() {
    console.log('discovering home loaction');
  }
}
