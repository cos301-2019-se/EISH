import { Component, OnInit } from '@angular/core';

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
  constructor() { }

  ngOnInit() {
  }

  /**
   * Determines which action to take depending on keyType
   */
  handleKey(key){
    if(this.keyType == "general")
      this.registerUser(key);
      else
        this.renewUser(key);
  }

  /**
   * Routes to userRegistration page
   */
  registerUser(key){

  }
  /**
   * Routes to system upon successful submission of renewal key
   */
  renewUser(key){

  }

}
