import { Component, OnInit, OnDestroy } from '@angular/core';
import { WeatherService } from 'src/app/services/weather/weather.service';
import { map, startWith} from 'rxjs/operators';
import { RxStompService } from '@stomp/ng2-stompjs';
import { Message} from '@stomp/stompjs';
import { Weather } from 'src/app/models/weather-model';
import { GeneratorService } from 'src/app/services/generators/generator.service';
import { pipe } from 'rxjs';
import { ConsumptionService } from 'src/app/services/consumption/consumption.service';
@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit, OnDestroy {

  batteryTopic: any;
  weatherTopic: any;
  dayConsumption: any;
  weekConsumption: any;
  monthConsumption: any;
  batteryStatus: string;
  batteryMode: string;
  batteryObject: any;

  constructor(
    private rxStompService: RxStompService, private weatherService: WeatherService,
    private generatorService: GeneratorService, private consumptionService: ConsumptionService) { }

  // for battery
    gaugeMin = 0;
    gaugemax = 100;
    gaugeCap = 'round';
    gaugeType = 'full';
    gaugeValue = 15;
    gaugeAppendText = '%';
    gaugeThickness = 8;
    state = 'Charging';
     weather: any;
    // ={
    //   weatherTemperature:-1,
    //   weatherIcon:'s01d',
    //   weatherDescription:'Snowy',
    //   weatherLocation: 'Pretoria'
    // }
    thresholdConfig = {
      0: {color: 'red'},
      5: {color: 'orange'},
      15: {color: 'yellow'},
      49: {color: 'yellowgreen'},
      80: {color: 'green'}
    };

  ngOnInit() {
    this.weatherService.getWeather().pipe(
      map(response => {
          this.weather = response,
          this.weather.weatherTemperature = Math.ceil(this.weather.weatherTemperature);
          console.log(this.weather);
        })

    ).subscribe();

    this.weatherTopic = this.rxStompService.watch('/weather').subscribe((message: Message) => {
      this.weather = JSON.parse(message.body);
      this.weather.weatherTemperature = Math.ceil(this.weather.weatherTemperature);
    });

    this.generatorService.getBatteryPercentage().pipe(
      map(response => {
        let battery: any;
        battery = ( response);
        console.log(battery);
        this.gaugeValue = battery.batteryCapacityPowerPercentage;

        if (this.gaugeValue >= 80 && this.gaugeValue < 100) {
          this.batteryStatus = 'Full';
          this.batteryMode = 'We\'re doing great!';
        } else if (this.gaugeValue >= 49 && this.gaugeValue < 80) {
          this.batteryStatus = 'Good';
          this.batteryMode = 'We\'re still okay';
        } else if (this.gaugeValue > 15 && this.gaugeValue < 50) {
          this.batteryStatus = 'Normal';
          this.batteryMode = 'We could be doing better. Please switch off unused devices';
        } else if (this.gaugeValue > 5 && this.gaugeValue < 16) {
          this.batteryStatus = 'Low';
          this.batteryMode = 'Save power by switching off unused and non-essential devices';
        } else {
          this.batteryStatus = 'Critcal';
          this.batteryMode = 'Energy usage limited to essential devices only.';
        }

      })
    ).subscribe();

    this.getConsumptionCardValues();

    /*this.batteryTopic = this.rxStompService.watch('/battery').subscribe((message: Message) => {
       this.batteryObject = JSON.parse(message.body);
       console.log(JSON.stringify(message.body));
       this.gaugeValue = this.batteryObject.percentage;

       if (this.gaugeValue >= 80 && this.gaugeValue < 100) {
         this.batteryStatus = 'Full';
         this.batteryMode = 'We\'re doing great!';
       } else if (this.gaugeValue >= 49 && this.gaugeValue < 80) {
         this.batteryStatus = 'Good';
         this.batteryMode = 'We\'re still okay';
       } else if (this.gaugeValue > 15 && this.gaugeValue < 50) {
         this.batteryStatus = 'Normal';
         this.batteryMode = 'We could be doing better. Please switch off unused devices';
       } else if (this.gaugeValue > 5 && this.gaugeValue < 16) {
         this.batteryStatus = 'Low';
         this.batteryMode = 'We are running low. Let\'s save power by switching off unused and non-essential devices';
       } else {
         this.batteryStatus = 'Critcal';
         this.batteryMode = 'We are in critical status. Energy usage limited to essential devices only.';
       }

     });*/

  }

  getConsumptionCardValues() {
    this.consumptionService.getDayTotalConsumption().pipe(
      map(response => {
        console.log('day: ' + JSON.stringify(response));
        this.dayConsumption = Math.ceil(response.totalDayHomeConsumption / 1000);
        console.log('dayConsumption: ' + this.dayConsumption);
      })
    ).subscribe();

    this.consumptionService.getWeekTotalConsumption().pipe(
      map(response => {
        console.log('week: ' + JSON.stringify(response));
        this.weekConsumption = Math.ceil(response.totalWeekHomeConsumption / 1000);
        console.log('weekConsumption: ' + this.weekConsumption);
      })
    ).subscribe();

    this.consumptionService.gethMontTotalConsumption().pipe(
      map(response => {
        console.log('month: ' + JSON.stringify(response));
        this.monthConsumption = Math.ceil(response.totalMonthHomeConsumption / 1000);
        console.log('monthConsumption: ' + this.monthConsumption);
      })
    ).subscribe();
  }

  ngOnDestroy() {
    /*this.batteryTopic.unsubscribe();
    this.weatherTopic.unsubscribe();*/
  }

}

