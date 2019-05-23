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
  constructor() { }
  
  ngOnInit() {
  }

drawTotalConsumption(){
  /*var data = [
        "date_time": 1549749600,
        "consumption": 1000
    },
    {
        "device_id": 1,
        "date_time": 1550613600,
        "consumption": 500
    },
    {
        "device_id": 2,
        "date_time": 1551304800,
        "consumption": 1000
    },
    {
        "device_id": 3,
        "date_time": 1551391200,
        "consumption": 200
    },
    {
        "device_id": 3,
        "date_time": 1552168800,
        "consumption": 1250
    },
    {
        "device_id": 1,
        "date_time": 1553983200,
        "consumption": 100
    }
  ]*/
   /*var graphLabels =[];
     data.forEach(element =>{
       var date = new Date(element.date_time*1000).toLocaleDateString();
      graphLabels.push(date);
    })
    /*var yAxis = [];
    data.forEach(element =>{
      yAxis.push(element.consumption)
    })
    console.log(yAxis)
    */
    /*var dataConsumption = [];
    data.forEach(element =>{
      dataConsumption.push({"x" : element.date_time, "y" : element.consumption})
    });
    
   console.log(dataConsumption)
   console.log(graphLabels)
    this.consumptionChart = new Chart(
      'totalConsumption', {
        type: 'line',
    data: {
        datasets: [{
            data: dataConsumption
        }]
    },
    options: {
      legend: {
        display:false
      },
        scales: {
            xAxes: [{
                type: 'time',
               DisplayFormats: {
                  'millisecond': 'MMM DD',
                  'second': 'MMM DD',
                  'minute': 'MMM DD',
                  'hour': 'MMM DD',
                  'day': 'MMM DD',
                  'week': 'MMM DD',
                  'month': 'MMM DD',
                  'quarter': 'MMM DD',
                  'year': 'MMM DD',
               }
            }]
        }
    }
  });*/
}
format_date(data:Consumption[]) : {}{
  var newData = {};

 data.forEach(element =>
 {  var date = new Date(element.date_time*1000);
    let formatted_date = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() 
   
  })
  return newData;
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
