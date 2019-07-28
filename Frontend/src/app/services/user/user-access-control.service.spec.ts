import { TestBed, inject } from '@angular/core/testing';
import { BrowserDynamicTestingModule, platformBrowserDynamicTesting } from '@angular/platform-browser-dynamic/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UserAccessControlService} from './user-access-control.service';
import { HttpEvent, HttpEventType} from '@angular/common/http';
import { User } from 'src/app/models/user-model';

describe('UserAccessControlService', () => {
  let service: UserAccessControlService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.resetTestEnvironment();
    TestBed.initTestEnvironment(BrowserDynamicTestingModule, platformBrowserDynamicTesting());

    TestBed.configureTestingModule({
          imports: [HttpClientTestingModule],
          providers: [UserAccessControlService]
        });

    service = TestBed.get(UserAccessControlService);
    httpMock = TestBed.get(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });
});


  /* Unit test for authenticateUser */
it('should successfully authenticate user', inject(
  [HttpTestingController, UserAccessControlService],
  (httpMock: HttpTestingController, userService: UserAccessControlService) => {
    const user: User = {
        userId: 6,
        userName: 'Charl',
        userPassword: '123456',
        userEmail: 'charl@email.com',
        userLocationTopic: 'location',
        userExpiryDate: '2019-12-31T21:59:59.000Z',
        userType: 'RESIDENT'
    };

    const test = userService.authenticateUser(user, this);
    const mockReq = httpMock.expectOne(userService.ROOT_URL);
    expect(mockReq.cancelled).toBeFalsy();
    expect(mockReq.request.responseType).toEqual('json');
    mockReq.flush(user);
})
);

/* Unit test for isUserLoggedIn*/
it('should confirm that user is correctly logged in', () => {

});

/*Unit test for userLogOut */
it('should log user out', () => {

});

/*Unit test for getting one user */
it('should retrieve user details', () => {

});

/*Unit test for changeCredentials */
it('should successully change users credentials', () => {

});

/*Unit test for registerUser */
it('should successully register new user', () => {

});

/* Unit test for authenticateKey */
it('should successfully authenticate key', () => {

});

/* Unit test for removeUser */
it('should successfully remove user', () => {

});

/* Unit test for retrieveAllUsers */
it('should retrieve all users', () => {

});

/* Unit test for changUserType */
it('', () => {

});

/* Unit test for changeUserExpiration */
it('', () => {

});
