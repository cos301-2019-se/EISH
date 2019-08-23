import { TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { BrowserDynamicTestingModule, platformBrowserDynamicTesting } from '@angular/platform-browser-dynamic/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UserAccessControlService} from './user-access-control.service';
import { HttpEvent, HttpEventType, HttpParams} from '@angular/common/http';
import { User } from 'src/app/models/user-model';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from '../../templates/forms/login/login.component';
import { SideNavComponent } from '../../side-nav/side-nav.component';
import { ChangeCredentialsComponent } from '../../templates/forms/change-credentials/change-credentials.component';
import { KeysComponent } from '../../templates/forms/keys/keys.component';
import { DashboardComponent } from '../../pages/dashboard/dashboard.component';
import { NgModule } from '@angular/core';
describe('UserAccessControlService', () => {
  let service: UserAccessControlService;

  beforeEach(() => {
    TestBed.resetTestEnvironment();
    TestBed.initTestEnvironment(BrowserDynamicTestingModule, platformBrowserDynamicTesting());

    TestBed.configureTestingModule({

      imports: [HttpClientTestingModule, RouterModule.forRoot([])] ,
      /*declarations: [ DashboardComponent] ,*/
      providers: [UserAccessControlService]
      });
  });


  it('should be created', () => {
      service = TestBed.get(UserAccessControlService);
      expect(service).toBeTruthy();
  });

  /*it('should successfully authenticate user', fakeAsync( inject(
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
        const mockReq = httpMock.expectOne('http://eishms.ddns.net:8080/api/auth/login/');
        expect(mockReq.cancelled).toBeFalsy();
        expect(mockReq.request.responseType).toEqual('json');
        mockReq.flush(user);
    })
    ));*/

  it('it should return true: user is logged in', fakeAsync( inject(
      [HttpTestingController, UserAccessControlService],
      (httpMock: HttpTestingController, userService: UserAccessControlService) => {
        sessionStorage.setItem('userName', 'Felicia');
        const response = userService.isUserLoggedIn();

        tick();
        expect(response).toBe(true);
      })
  ));

  it('it should return false: user is not logged in', fakeAsync( inject(
    [HttpTestingController, UserAccessControlService],
    (httpMock: HttpTestingController, userService: UserAccessControlService) => {
      sessionStorage.clear();
      const response = userService.isUserLoggedIn();

      tick();
      expect(response).toBe(false);
    })
  ));

  it('it should log the user out; sessionStorage length must be zero', fakeAsync( inject(
    [HttpTestingController, UserAccessControlService],
    (httpMock: HttpTestingController, userService: UserAccessControlService) => {
      sessionStorage.setItem('userName', 'Felicia');
     userService.userLogOut();

      tick();
      expect(sessionStorage.length).toBe(0);
    })
  ));

  it('gets user in the system using sessionStorage username', fakeAsync(inject(
    [HttpTestingController, UserAccessControlService],
    (httpMock: HttpTestingController, userService: UserAccessControlService) => {
      sessionStorage.setItem('userName', 'Anna');
      const mockUser = [
        {
          userId: 1,
          userName: 'Anna',
          userEmail: 'anna@email.com',
          userLocationTopic: 'location',
          userExpiryDate: '2019-12-31T21:59:59.000Z',
          userType: 'ROLE_RESIDENT'
        }];

      userService.getUser().subscribe(
        actualusers => {
         
          expect(actualusers[0].userId).toEqual(1);
          expect(actualusers[0].userName.toEqual('Anna'));
        }
      );

      tick();
      /*const username = sessionStorage.getItem('userName');
      const params = new HttpParams().set('userName', username);
      const req = httpMock.expectOne('http://eishms.ddns.net:8080/api/user/' + {params});
      expect(req.request.method).toEqual('GET');

      req.flush(mockUser);
      httpMock.verify();*/

    })

  ));
  /**
   * changeCredentials()
   * registerUser()
   * authenticateKey()
   * removeUser()
   * getAllUsers()
   * getUserPresence()
   * changeUserType()
   * changeUserExpiration()
   * canActivate()
   */
});

