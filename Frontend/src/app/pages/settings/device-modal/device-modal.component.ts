import { Component, OnInit, Inject } from '@angular/core';
import {MAT_DIALOG_DATA,MatDialogRef} from "@angular/material";
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
@Component({
  selector: 'app-device-modal',
  templateUrl: './device-modal.component.html',
  styleUrls: ['./device-modal.component.css']
})
export class DeviceModalComponent implements OnInit {
  addDeviceForm: FormGroup;
  devicePriorities: String[];
  selected: any;
  constructor(private fb:FormBuilder,private dialogRef:MatDialogRef<DeviceModalComponent>) {

   }

  ngOnInit() {
    this.addDeviceForm = this.fb.group({
      deviceName:[null,[Validators.required]],
      deviceTopic:[null,[Validators.required]],
      devicePriority:["None",[Validators.required]]
    })

  }

  addDevice(){
    if(this.addDeviceForm.invalid)
    {
      return;
    }
    else{    
    this.dialogRef.close(this.addDeviceForm.value);
    }
  }


}
