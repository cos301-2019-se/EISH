import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Weather } from 'src/app/models/weather-model';
import { environment } from 'src/environments/environment';
@Injectable({
  providedIn: 'root'
})
export class WeatherService {

  WEATHER_API = environment.ROOT_URL;
  constructor(private http: HttpClient) { }

  getWeather(): Observable<Weather[]> {
    console.log('heeeeeeeyyyyyyyyyyyy');
    return this.http.get<Weather[]>(this.WEATHER_API);
  }
}
