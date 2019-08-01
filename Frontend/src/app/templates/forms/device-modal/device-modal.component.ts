import { Component, OnInit, Inject } from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { DeviceService } from 'src/app/services/devices/device.service';

@Component({
  selector: 'app-device-modal',
  templateUrl: './device-modal.component.html',
  styleUrls: ['./device-modal.component.css']
})
export class DeviceModalComponent implements OnInit {
  addDeviceForm: FormGroup;
  devicePriorities: string[];
  selected: any;
  constructor(private fb: FormBuilder, private dialogRef: MatDialogRef<DeviceModalComponent>, private deviceService: DeviceService) {

   }

  ngOnInit() {
    this.addDeviceForm = this.fb.group({
      deviceName: [null, [Validators.required]],
      deviceTopic: [null, [Validators.required]],
      devicePriorityType: [null, [Validators.required]]
    });

  }

  addDevice() {
    if (this.addDeviceForm.invalid) {

      return;
    } else {

    const device = {
      deviceName: this.addDeviceForm.get('deviceName').value,
      deviceTopic: this.addDeviceForm.get('deviceTopic').value,
      devicePriorityType: this.addDeviceForm.get('devicePriorityType').value,
      // tslint:disable-next-line: quotemark
      deviceStates: ["ON", "OFF", "OFFLINE"]
    };
    console.log(device);
    this.deviceService.addDevice(device);
    this.dialogRef.close(this.addDeviceForm.value);
    }
  }
}
