import { TestBed, inject, getTestBed, fakeAsync, tick } from '@angular/core/testing';
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
          providers: [DeviceService]
        });
  });

  it('should be created', () => {
      service = TestBed.get(DeviceService);
      expect(service).toBeTruthy();
  });

  it('gets all devices in the system', fakeAsync(inject(
    [HttpTestingController, DeviceService],
    (httpMock: HttpTestingController, deviceService: DeviceService) => {
      const mockDevices = [
        {
          deviceId: 1,
          deviceName: 'Sunbeam Kettle',
          deviceTopic: 'sunbeam',
          devicePriorityType: 'PRIORITY_NEUTRAL',
          deviceStates: '{\'ON\', \'OFF\'}'
        },
        {
            deviceId: 3,
            deviceName: 'LG Dishwasher',
            deviceTopic: 'lg',
            devicePriorityType: 'PRIORITY_NICETOHAVE',
            deviceStates: '{\'ON\', \'OFF\', \'STANDBY\'}'
        }
      ];

      deviceService.getAllDevices().subscribe(
        actualDevices => {
          expect(actualDevices.length).toBe(2);
          expect(actualDevices[0].deviceId).toEqual(1);
        }
      );

      tick();
      const req = httpMock.expectOne('http://eishms.ddns.net:8080/api/devices');
      expect(req.request.method).toEqual('GET');

      req.flush(mockDevices);
      httpMock.verify();

    })

  ));

  /*it('it adds a device to the system', fakeAsync(inject(
    [HttpTestingController, DeviceService],
    (httpMock: HttpTestingController, deviceService: DeviceService) => {
        const device = {
          deviceName: this.addDeviceForm.get('deviceName').value,
          deviceTopic: this.addDeviceForm.get('deviceTopic').value,
          devicePriorityType: this.addDeviceForm.get('devicePriorityType').value,
          // tslint:disable-next-line: quotemark
          deviceStates: ["ON", "OFF", "OFFLINE"]
        };

        deviceService.addDevice(device);
        tick();
        const req = httpMock.expectOne('http://eishms.ddns.net:8080/api/device');
        expect(req.request.method).toEqual('POST');

        expect(req.status).toBe(200);
        httpMock.verify();
      })
    )
  );*/

  it('it edits a device in the system', fakeAsync(inject(
    [HttpTestingController, DeviceService],
    (httpMock: HttpTestingController, deviceService: DeviceService) => {
        const device = {
          deviceId: 1,
          deviceName: 'Sunbeam Kettle',
          deviceTopic: 'sunbeam',
          devicePriorityType: 'PRIORITY_NEUTRAL',
          // tslint:disable-next-line: quotemark
          deviceStates: ["ON", "OFF", "OFFLINE"]
        };
        let response;

        deviceService.editDevice(device).subscribe(
          (receivedResponse: any) => {
            response = receivedResponse.statusCode;
          },
          (error: any) => {}
        );
        tick();
        const req = httpMock.expectOne('http://eishms.ddns.net:8080/api/device');
        expect(req.request.method).toEqual('PUT');

        
        httpMock.verify();
      })
    )
  );

});

