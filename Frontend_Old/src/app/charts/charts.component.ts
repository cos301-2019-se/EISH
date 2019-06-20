import { Component, OnInit, Injectable } from '@angular/core';
import {Chart} from 'chart.js';
import { Consumption } from '../consumption.component';
import { formatDate } from '@angular/common';
import { element } from '@angular/core/src/render3';
import { flattenStyles } from '@angular/platform-browser/src/dom/dom_renderer';

@Component({
  selector: 'app-charts',
  templateUrl: './charts.component.html',
  styleUrls: ['./charts.component.css']
})
@Injectable({
  providedIn: 'root'
})
export class ChartsComponent implements OnInit {

  batteryChart = [];
  consumptionChart = [];
  totalConsumption = [];
  deviceGraph = [];
  constructor() { }
  
  ngOnInit() {
  }

drawTotalConsumption(){
    this.totalConsumption = new Chart('totalConsumption',{
      type: 'line',
  data: {
    labels: ["17 May", "18 May", "19 May", "20 May", "22 May", "23 May", "24 May"],
    datasets: [{ 
        data: [1150,114,108,106,10,1111,133,221,0],
        label: "Fridge",
        borderColor: "#008000",
        fill: false
      }, 
    ]
  },
    options: {
        legend: {
          display: false
        }
      }
    
  });
}
drawDeviceConsumption(){
 this.consumptionChart = new Chart('deviceConsumption', {
  type: 'line',
  data: {
    labels: ["17 May", "18 May", "19 May", "20 May", "22 May", "23 May", "24 May"],
    datasets: [{ 
        data: [860,114,106,106,107,111,133,221,783,900],
        label: "Fridge",
        borderColor: "#DCDCDC",
        fill: false
      }, { 
        data: [282,350,411,502,635,809,947,1402,370,527],
        label: "Light",
        borderColor: "#1B982C",
        fill: false
      }, { 
        data: [168,170,178,190,203,276,408,547,675,734],
        label: "Kettle",
        borderColor: "#000000",
        fill: false
      }, { 
        data: [400,200,100,160,240,380,74,167,508,784],
        label: "Blender", 
        borderColor:  "#DCDCDC",
        fill: false
      }, { 
        data: [600,500,600,1086,700,260,820,1702,3102,433],
        label:"Iron",
        borderColor: "#008000",
        fill: false
      }
    ]
  },
  options: {
    title: {
      display: true,
      text: "Device consumption (watts)", 
      position: 'bottom'
    },
    legend: {
      display: false
    },
    scales: {
      xAxes: [{
              display: true,
              scaleLabel: {
                  display: true,
                  labelString: 'Day'
              }
          }],
      yAxes: [{
              display: true,
              ticks: {
                  beginAtZero: true,
                  steps: 10,
                  stepValue: 5,
                  max: 1400
              }
          }]
  }
  }
});
}

drawDeviceGraph(){
  this.deviceGraph = new Chart('devicesGraph', {
    responsive:true,
    maintainAspectRatio: false,
    type: 'bar',
    data: {
      labels: ["Fridge", "Light", "Kettle", "Blender", "Iron"],
      datasets: [
        {
          label: "Consumption (watts)",
          backgroundColor: ["#008000", "#DCDCDC","#000000","#1B982C","#DCDCDC"],
          data: [2478,5267,734,784,433]
        }
      ]
    },
    options: {
      legend: { display: false }

    }
});
}

drawBatteryCapacity(){
    
    this.batteryChart = new Chart('batteryCapacity', {
      type: 'doughnut',
      data:{
        labels: ["available", "Empty"]
        ,datasets: [{
          data: [85,15],
          backgroundColor: ["#4BA157", "#D3D3D3" ]
        }]
      },
      options:{
        legend: {
          display: false
        }
      }
  });
  }
}
