import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Weather } from 'src/app/models/weather-model';
@Injectable({
  providedIn: 'root'
})
export class WeatherService {

  WEATHER_API = 'http://192.168.8.102:8080/api/weather';
  constructor(private http: HttpClient) { }

  getWeather():Observable<Weather[]>{
    return this.http.get<Weather[]>(this.WEATHER_API);
  }
}
