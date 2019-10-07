import { TestBed } from '@angular/core/testing';

import { WeatherService } from './weather.service';
import { BrowserDynamicTestingModule, platformBrowserDynamicTesting } from '@angular/platform-browser-dynamic/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('WeatherService', () => {
  beforeEach(() => {
    TestBed.resetTestEnvironment();
    TestBed.initTestEnvironment(BrowserDynamicTestingModule, platformBrowserDynamicTesting());

    TestBed.configureTestingModule({
          imports: [HttpClientTestingModule],
          providers: [WeatherService]
        });
  });

  it('should be created', () => {
    const service: WeatherService = TestBed.get(WeatherService);
    expect(service).toBeTruthy();
  });
});
