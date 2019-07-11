import { Component, OnInit } from '@angular/core';
import { Chart } from 'chart.js';
import { Device } from 'src/app/models/device-model';

@Component({
  selector: 'app-consumption-chart',
  templateUrl: './consumption-chart.component.html',
  styleUrls: ['./consumption-chart.component.css']
})
export class ConsumptionChartComponent implements OnInit {

  /**
   * Variables:
   */
  //deviceChart: any;
  //totalConsumption: any;
  //deviceName : 
  //deviceName = "";
  heading: String;
  chart: Chart;
  currentDevice = null;
  //consumptionData = null;
  //consumptionOptions = null;

  chartConfigration = null;

  constructor() { 
    this.heading = "Home Consumption";

    //this.deviceName = "Mara Angular";
  }

  ngOnInit() {
    this.configureConsumptionChart();
  }

  configureConsumptionChart() { 
    this.chartConfigration = {
      type: 'line',
      data: {
        labels: [1,2,3,4,5],
        datasets: [{
          label: 'Device Consumption',
          backgroundColor: 'rgba(24,48,35,0.5)',
          borderColor: '#FF22DD',
          data: [ 2, 8, 6, 7, 0
          ],
          fill: true,
        }]
      },
      options: {
        responsive: true,
        title: {
          display: true,
          text: 'Some Device Consumption'
        },
        tooltips: {
          mode: 'index',
          intersect: false,
        },
        hover: {
          mode: 'nearest',
          intersect: true
        },
        scales: {
          xAxes: [{
            display: true,
            scaleLabel: {
              display: true,
              labelString: 'Time'
            }
          }],
          yAxes: [{
            display: true,
            scaleLabel: {
              display: true,
              labelString: 'Power Usage'
            }
          }]
        }
      }
    };
    this.chart = new Chart('consumptionChart', this.chartConfigration);
    this.addDataPoint({timestamp: 6, consumption: 12});
    this.setChartHeading('LightBulb Consumption');
    this.setXAxisLabel("Hours");
    this.setYAxisLabel("Power Consumption");
  }

  addDataPoint(newData): void {
    this.chartConfigration.data.labels.push(newData.timestamp);//put timestamp here
    this.chartConfigration.data.datasets[0].data.push(parseInt(newData.consumption));
    //this.chart.update();
  }
  
  setYAxisLabel(newLabel): void {
    this.chartConfigration.options.scales.yAxes[0].scaleLabel.labelString = newLabel;
    this.chart.update();
  }

  setXAxisLabel(newLabel): void {
    this.chartConfigration.options.scales.xAxes[0].scaleLabel.labelString = newLabel;
    this.chart.update();
  }

  setChartHeading(newLabel): void {
    this.chartConfigration.options.title.text = newLabel;
    this.chart.update();
  }

  addBulkData(bulkData): void {
    bulkData.foreach((dataPoint) => {
      this.addDataPoint(dataPoint);
    });

    //maybe update or ???
  }

  changeTimeFormat(): void {

  }

  changeTimeScale(): void {

  }

  clearChart(): void {
    this.chartConfigration.data.labels = [];
    this.chartConfigration.data.datasets[0].data = [];
  }

  // changeConsumptionChart(deviceName, ): void {

  // }
  /**
   * Draw graph for total consumption of all devices
   */
  drawTotalConsumption(){

  }

  /**
   * Draw graph for total consumption of a specific devices
   */
  drawDeviceConsumption(){

  }

}
