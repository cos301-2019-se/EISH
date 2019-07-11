import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  ///for battery
  gaugeMin =0;
  gaugemax = 100;
  gaugeCap ="round"
  gaugeType = "full";
  gaugeValue = 82;
  gaugeLabel = "Battery Percentage";
  gaugeAppendText = "%";
  gaugeThickness = 4;
  state="Charging"

  //for weather
  minTemp = 15;
  maxTemp = 28;
  currentTemp = 23;
  condition = "Storm"
  constructor() { }

  ngOnInit() {
  }

  thresholdConfig = {
    '10': {color:'red'},
    '20' : {color: 'orange'},
    '40': {color:'yellow'},
    '80': {color:'green'}
  };
}
