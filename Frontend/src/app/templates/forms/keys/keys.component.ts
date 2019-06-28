import { Component, OnInit } from '@angular/core';
import { FormGroup,FormBuilder, Validators } from '@angular/forms';
import {UserAccessControlService} from 'src/app/services/user/user-access-control.service';
import {Router,ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-keys',
  templateUrl: './keys.component.html',
  styleUrls: ['./keys.component.css']
})
export class KeysComponent implements OnInit {
  /**
   * Class routes to register and renews guest access
   */
  keyType:string //general or renew
  keyForm: FormGroup;
  incorrectKey: boolean;
  constructor(private routes: Router,private fb: FormBuilder,private authenticationService: UserAccessControlService) {
    this.incorrectKey = false;
    let formHeading = "Key Form";
    this.keyForm = this.fb.group({
      'userKey':[null,[Validators.required]]
    }); 
  }

  ngOnInit() {
    this.incorrectKey = false;
    let formHeading = "Key Form";
    this.keyForm = this.fb.group({
      'userKey':[null,[Validators.required]]
    });
  }
  route(){
    this.routes.navigate(['register','Register']);
  }
  errorInForm(){
    this.incorrectKey = true;
  }
  get getVariables(){
    return this.keyForm.controls;
  }
  /**
   * Determines which action to take depending on keyType
   */
  handleKey(formData){
    this.authenticationService.authenticateKey('general',formData.value, this);
 }

}
