import { Component, OnInit } from '@angular/core';
import { Chart } from 'chart.js';
// import { Device } from 'src/app/models/device-model';

@Component({
  selector: 'app-consumption-chart',
  templateUrl: './consumption-chart.component.html',
  styleUrls: ['./consumption-chart.component.css']
})
export class ConsumptionChartComponent implements OnInit {

  /**
   * Variables:
   */
  heading: String;
  chart: Chart;
  currentDevice = null;

  chartConfigration = null;

  constructor() { 
    this.heading = "Home Consumption";
    //connect to a socket
  }

  ngOnInit() {
    this.configureConsumptionChart();
  }

  setHeading(heading: String) {
    this.heading = heading;
  }

  configureConsumptionChart() { 
    this.chartConfigration = {
      type: 'line',
      data: {
        labels: [],
        datasets: [{
          label: 'Device Consumption',
          backgroundColor: 'rgba(93, 217, 93, 0.6)',
          borderColor: 'rgba(93, 217, 93, 1)',
          data: [
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
    this.setChartHeading('Consumption');
    //this.setXAxisLabel("Hours");
    this.setYAxisLabel("Power Consumption");
  }

  addDataPoint(newData): void {
    this.chartConfigration.data.labels.push(newData.deviceConsumptionTimestamp);//put timestamp here
    this.chartConfigration.data.datasets[0].data.push(parseInt(newData.deviceConsumption));
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
    this.clearChart();
    bulkData.forEach((dataPoint) => {
      this.addDataPoint(dataPoint);
    });

    //maybe update or ???
    this.chart.update();
  }

  changeTimeFormat(): void {

  }

  changeTimeScale(): void {

  }

  detectTimeScale(consumptionData): void {

  }

  clearChart(): void {
    this.chartConfigration.data.labels = [];
    this.chartConfigration.data.datasets[0].data = [];
  }

  updateChart(): void {
    this.chart.update();
  }

}
