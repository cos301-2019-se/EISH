import { Component, OnInit } from '@angular/core';
import  { MatSidenavModule,MatMenuModule} from  '@angular/material';

const material = [MatSideNavModule,MatMenuModule];
@Component({
  selector: 'app-materials',
  templateUrl: './materials.component.html',
  styleUrls: ['./materials.component.css']
})
export class MaterialsComponent implements OnInit {

  constructor() { }
  
  ngOnInit() {
  }

}
