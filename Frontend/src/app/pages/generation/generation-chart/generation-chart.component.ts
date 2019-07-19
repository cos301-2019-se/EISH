import { Component, OnInit, ViewChild } from '@angular/core';
import { RxStompService } from '@stomp/ng2-stompjs';
import { GeneratorService } from 'src/app/services/generators/generator.service';
import Chart from 'chart.js';

@Component({
  selector: 'app-generation-chart',
  templateUrl: './generation-chart.component.html',
  styleUrls: ['./generation-chart.component.css']
})
export class GenerationChartComponent implements OnInit {

  heading: string;
  chart: Chart;
  currentDevice = null;

  chartConfigration = null;

  constructor() {
    this.heading = 'Home Generation';
    // connect to a socket
  }

  ngOnInit() {
    this.configureConsumptionChart();
  }

  setHeading(heading: string) {
    this.heading = heading;
  }

  configureConsumptionChart() {
    this.chartConfigration = {
      type: 'line',
      data: {
        labels: [1, 2, 3, 4, 5],
        datasets: [{
          label: 'Generator Generation',
          backgroundColor: 'rgba(250, 63, 30, 0.6)',
          borderColor: 'rgba(250, 63, 30, 1)',
          data: [ 2, 8, 9, 4, 2
          ],
          fill: true,
        }]
      },
      options: {
        responsive: true,
        legend: {
            labels: {
                // This more specific font property overrides the global property
                fontFamily: 'Raleway'
            }
        },
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
    this.chart = new Chart('generationChart', this.chartConfigration);
    this.setChartHeading('Generation');
    // this.setXAxisLabel("Hours");
    this.setYAxisLabel('Power Generation');
  }

  addDataPoint(newData): void {
    if (typeof newData.generatorGenerationTimestamp != 'undefined') {
      this.chartConfigration.data.labels.push(newData.generatorGenerationTimestamp); // put timestamp here
    } else {
      this.chartConfigration.data.labels.push(newData.homeGenerationTimeStamp);
    }

    if (typeof newData.generatorGeneration != 'undefined') {
      // tslint:disable-next-line: radix
      this.chartConfigration.data.datasets[0].data.push(parseInt(newData.generatorGeneration));
    } else {
      // tslint:disable-next-line: radix
      this.chartConfigration.data.datasets[0].data.push(parseInt(newData.homeGeneration));
    }
    // this.chart.update();
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

  setDatasetHeading(newLabel) {
    this.chartConfigration.data.datasets[0].label = newLabel;
    this.chart.update();
  }

  addBulkData(bulkData): void {
    this.clearChart();
    bulkData.forEach((dataPoint) => {
      this.addDataPoint(dataPoint);
    });

    // maybe update or ???
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
