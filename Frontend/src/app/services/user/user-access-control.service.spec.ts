import { TestBed } from '@angular/core/testing';
import { BrowserDynamicTestingModule, platformBrowserDynamicTesting } from '@angular/platform-browser-dynamic/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UserAccessControlService} from './user-access-control.service';
import { HttpEvent, HttpEventType} from '@angular/common/http';

beforeEach(() => {
  TestBed.resetTestEnvironment();
  TestBed.initTestEnvironment(BrowserDynamicTestingModule, platformBrowserDynamicTesting());

  TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [UserAccessControlService]
      });
   });

    /* Unit test for authenticateUser */
it('should successfully authenticate user', () => {
    const service: UserAccessControlService = TestBed.get(UserAccessControlService);
    let  httpMock = TestBed.get(HttpClientTestingModule);

    httpMock = {
      username: 'felicia',
      password: '12345',
      email: 'felicia@mail.com'
    };
    service.authenticateUser(httpMock).subscribe((event: HttpEvent<any>) => {
      switch (event.type) {
        case HttpEventType.Response:
          expect(event.body).toEqual(httpMock);
      }
      expect(service).toBeTruthy();
      const mockReq = httpMock.expectOne(service.ROOT_URL);

      expect(mockReq.cancelled).toBeFalsy();
      expect(mockReq.request.responseType).toEqual('json');
      mockReq.flush(httpMock);

      httpMock.verify();
  });
});

/* Unit test for isUserLoggedIn*/
it('should confirm that user is correctly logged in', () => {

});

/*Unit test for userLogOut */
it('should log user out', () => {

});

/*Unit test for changeCredentials */
it('should successully change users credentials', () => {

});

/*Unit test for registerUser */
it('should successully register new user', () => {

});

/* authenticateKey */
it('should successfully authenticate key', () => {

});
