import { TestBed } from '@angular/core/testing';

import { ConsumptionService } from './consumption.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { BrowserDynamicTestingModule, platformBrowserDynamicTesting } from '@angular/platform-browser-dynamic/testing';

describe('ConsumptionService', () => {
  beforeEach(() => {
    TestBed.resetTestEnvironment();
    TestBed.initTestEnvironment(BrowserDynamicTestingModule, platformBrowserDynamicTesting());

    TestBed.configureTestingModule({
          imports: [HttpClientTestingModule],
          providers: [ConsumptionService]
        });
  });

  it('should be created', () => {
    const service: ConsumptionService = TestBed.get(ConsumptionService);
    expect(service).toBeTruthy();
  });

  
  it('get today\'s total consumption', fakeAsync(inject(
      [HttpTestingController, ConsumptionService],
      (httpMock: HttpTestingController, consumptionService: ConsumptionService) => {
        const consumption = [
          homeConsumptionTimeStamp: "2019-07-11 20:47:11",
          homeConsumption: 10.0
      ];
    
      consumptionService.getDayTotalConsumption(
        actualDevices => {
          expect(actualDevices.length).toBe(2);
          expect(actualDevices[0].deviceId).toEqual(1);
        }
      );

      tick();
      const req = httpMock.expectOne('http://eishms.ddns.net:8080/api/home/consumption/day');
      expect(req.request.method).toEqual('GET');

      req.flush(mockDevices);
      httpMock.verify();
      })));

});
