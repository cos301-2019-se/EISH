import { TestBed, inject, getTestBed } from '@angular/core/testing';
import { DeviceService } from './device.service';
import { BrowserDynamicTestingModule, platformBrowserDynamicTesting } from '@angular/platform-browser-dynamic/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { Device } from 'src/app/models/device-model';
// import { Device } from 'src/app/models/device-model';

describe('DeviceService', () => {
  let service: DeviceService;

  beforeEach(() => {
    TestBed.resetTestEnvironment();
    TestBed.initTestEnvironment(BrowserDynamicTestingModule, platformBrowserDynamicTesting());

    TestBed.configureTestingModule({
          imports: [HttpClientTestingModule],
          providers: [DeviceService, HttpTestingController]
        });
  });

  it('should be created', () => {
      service = TestBed.get(DeviceService);
      expect(service).toBeTruthy();
  });
});

it('gets all devices in the system', inject(
  [HttpTestingController, DeviceService],
  (httpMock: HttpTestingController, deviceService: DeviceService) => {
    const mockDevices = [
      {
        deviceId: 1,
        deviceName: 'Sunbeam Kettle',
        deviceTopic: 'sunbeam',
        devicePriorityType: 'PRIORITY_NEUTRAL',
        deviceStates: '{\'ON\', \'OFF\'}'
      }
    ];

    deviceService.getAllDevices().subscribe(
      actualDevices => {
        expect(actualDevices.length).toBe(1);
        expect(actualDevices[0].deviceId).toEqual(1);
      }
    );
    const req = httpMock.expectOne(deviceService.ROOT_URL);
    expect(req.request.method).toEqual('GET');

    req.flush(mockDevices);
    httpMock.verify();
  })

);
