
import { TestBed, inject, getTestBed, fakeAsync, tick } from '@angular/core/testing';
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

  
 

});
