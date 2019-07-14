import { Component, OnInit } from '@angular/core';
import { GeneratorService } from 'src/app/services/generators/generator.service';

@Component({
  selector: 'app-battery-chart',
  templateUrl: './battery-chart.component.html',
  styleUrls: ['./battery-chart.component.css']
})
export class BatteryChartComponent implements OnInit {

  /*Variables*/
  gaugeType = 'semi';
  gaugeValue: BigInteger; // value needs to be retrieved
  gaugeLabel = 'Speed';
  gaugeAppendText = 'km/hr';

  constructor() { }

  ngOnInit() {
  }

}
