import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UserAccessControlService } from '../user/user-access-control.service';
import { AccessControlInterceptor } from './user-access-control.interceptor';
import { HTTP_INTERCEPTORS } from '@angular/common/http';

describe('AccessControlInterceptor', () => {
    let service: UserAccessControlService;
    let httpMock: HttpTestingController;

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [HttpClientTestingModule],
            providers: [UserAccessControlService, 
                        {
                            provide: HTTP_INTERCEPTORS,
                            useClass: AccessControlInterceptor,
                            multi: true
                        }]
        });

        service = TestBed.get(UserAccessControlService);
        httpMock = TestBed.get(HttpTestingController);
    });

    it('should add an authorization header', () => {
        let key = 'userToken';
        const userToken = sessionStorage.getItem(key);
        const fullToken = 'Bearer ' + userToken;

        //This checks whether we can make a request or not.
        //service.changeCredentials().subscribe(response => {
        //    expect(response).toBeTruthy();
        //});

        //Creates a mock http request.
        const httpRequest = httpMock.expectOne(`${service.ROOT_URL}/v1/user`);

        //Checks whether the mocked http request has an header that must been added by the interceptor.
        expect(httpRequest.request.headers.has('Authorization')).toBe(true);

        //Checks whether the mocked http request contains the same token request as the one saved.
        expect(httpRequest.request.headers.get('Authorization')).toBe(fullToken);
    });    
});

