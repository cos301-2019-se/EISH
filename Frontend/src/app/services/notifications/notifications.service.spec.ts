import { TestBed } from '@angular/core/testing';

import { NotificationsService } from './notifications.service';
import { BrowserDynamicTestingModule, platformBrowserDynamicTesting } from '@angular/platform-browser-dynamic/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RxStompService } from '@stomp/ng2-stompjs';

describe('NotificationsService', () => {
  beforeEach(() => {
    TestBed.resetTestEnvironment();
    TestBed.initTestEnvironment(BrowserDynamicTestingModule, platformBrowserDynamicTesting());

    TestBed.configureTestingModule({
          imports: [HttpClientTestingModule],
          providers: [NotificationsService, RxStompService]
        });
  });


  it('should be created', () => {
    const service: NotificationsService = TestBed.get(NotificationsService);
    expect(service).toBeTruthy();
  });
});
