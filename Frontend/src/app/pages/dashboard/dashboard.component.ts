import { Component, OnInit } from '@angular/core';
import { WeatherService } from 'src/app/services/weather/weather.service';
import {map, startWith} from 'rxjs/operators';
@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  title="Dashboard";
  ///for battery
  gaugeMin =0;
  gaugemax = 100;
  gaugeCap ="round"
  gaugeType = "full";
  gaugeValue = 92;
  //gaugeLabel = "Battery Percentage";
  gaugeAppendText = "%";
  gaugeThickness = 8;
  state="Charging"

  //for weather
  
  weatherData= {
    "weatherDescription": "Sunny",
    "weatherIcon":"http://openweathermap.org/img/wn/01n@2x.png",
    "weatherTemp":25,
    "weatherLocation": "Pretoria"
  };

  constructor(private weatherService: WeatherService) { }

  ngOnInit() {
    /*this.weatherService.getWeather().pipe(
        map( response => {
            this.weatherData =  response,
            JSON.stringify(this.weatherData),
            console.log(this.weatherData);
          })
    )*/
  }

  thresholdConfig = {
    '0': {color:'red'},
    '20' : {color: 'orange'},
    '40': {color:'yellow'},
    '80': {color:'green'}
  };
}
