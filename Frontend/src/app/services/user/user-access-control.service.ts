import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import 'rxjs/add/operator/map';
import { map, catchError } from 'rxjs/operators';
import {Observable, pipe} from 'rxjs';
import { User } from 'src/app/models/user-model';

@Injectable({
  providedIn: 'root'
})
export class UserAccessControlService {
  /**
   * Class executes HTTP requests to EISHMS API
   */

 /* Variables: */
  ROOT_URL = 'http://192.168.8.111:8080/api/';
  data: any;
  JSON_URL = 'assets/data/';
  constructor( private http: HttpClient) { }

  /**
   * Sends username and password to endpoint and receives JWT upon success
   * Uses session storage to store username and JWT
   * POST Request
   * exposed endpoint:
   * @param userCredentials: json of user form data
   * @returns Boolean
   */
  authenticateUser(userCredentials, loginInstance) {
    const LoginCred = {username : userCredentials.userName, password : userCredentials.userPassword};

    return this.http.post(this.ROOT_URL + 'auth/login/', LoginCred).pipe(
      map( response => {
            this.data =  response,
            sessionStorage.setItem('accessToken', this.data.accessToken),
            sessionStorage.setItem('userName' , userCredentials.userName);

            // check if details are admin if true:
            if (userCredentials.userName === 'admin') {
              loginInstance.routeToChange();
            } else { // else go to dashboard
              this.getUser().pipe(
                // tslint:disable-next-line: no-shadowed-variable
                map(res => {
                  const userType = res.userType;
                  sessionStorage.setItem('userType' , userType);
                })
              ).subscribe();
              loginInstance.routeToHomepage();
            }

      }), catchError(error =>
        loginInstance.error())
        ).subscribe();

  }

  /**
   * Checks session storage to see if user is currently logged in
   * @returns Boolean
   */
  isUserLoggedIn(): boolean {
    return sessionStorage.getItem('userName') != null;
  }

  /**
   * Clears session storage, invalidates JWT
   * Route to login page
   * PUT Request
   */
  userLogOut(): void {
    sessionStorage.clear(); // window.sessionStorage.clear();
    // load login page

  }

  /**
   * Retrieves user information from API
   * GET Request
   * exposed endpoint:
   * @param userCredentials: json of user form data
   * @returns Observerable array of type User
   */
  getUser(): Observable<User> {

    const username = sessionStorage.getItem('userName');
    const params = new HttpParams().set('userName', username);
    return this.http.get<User>(this.ROOT_URL + 'user/', { params });
  }

  /**
   * Sends user information to API
   * PUT Request
   * exposed endpoint:
   * @param userCredentials: json of user form data
   * @returns void
   */
  changeCredentials(userCredentials, credentialInstance) {

    this.http.put(this.ROOT_URL + 'user/', userCredentials).subscribe(
     ( res: Response) => {
       if (res.ok) {
        credentialInstance.route('dashboard', '');
       } else {
        credentialInstance.route('register', 'Change');
       } // error message?
    });
  }

  /**
   * Sends user information to API, receives JWT upon success
   * POST Request
   * exposed endpoint:
   * @param credential Object
   * @returns A message of whether a user was succesfully registered or not.
   */
  registerUser(userCredentials, registerInstance): any {

    return this.http.post(this.ROOT_URL + 'user/', userCredentials).pipe(
      map(
        response => {
            this.data =  response,
            sessionStorage.clear();
            registerInstance.route('/', '');
          }
      ), catchError(error =>
        registerInstance.error())
        ).subscribe();
  }

  /**
   * Authenticates given key and upon successful validation receives JWT
   * POST Request
   * exposed endpoint:
   * @param keyType Whether it is the general key or renewal key.
   * @param key key provided by the user.
   * @param keyInstance The component where this information is from.
   * @returns a message and JSON Web Token.
   */
  authenticateKey(keyType, key, keyInstance): any {
    const keyLogin = {username: keyType, password: key.userKey};

    return this.http.post(this.ROOT_URL + 'auth/login/', keyLogin).pipe(
      map(
        response => {
            this.data =  response,
            sessionStorage.setItem('accessToken', this.data.accessToken),
            sessionStorage.setItem('key', key);
            keyInstance.route();
          }
      ), catchError(error =>
       keyInstance.errorInForm())).subscribe();
  }

  /**
   * Removes user given userId
   */
  removeUser(userId): Observable<any> {
    console.log('in delete, userId: ' + userId);
    return this.http.delete(this.ROOT_URL + 'user/' + userId);
  }

  /**
   * Retrieve array all registered users on the system
   */
  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.ROOT_URL + 'users');
  }

  /**
   * Retrieves users' presence
   */
  getUserPresence(): Observable<User []> {
    console.log('user presence');
    return this.http.get<User []>(this.ROOT_URL + '');
  }

  /**
   * Change user role/type
   */
  changeUserType(userDetails) {
    // {userId}/{userType}
    // console.log(userDetails.userId)
    // console.log(userDetails.userType)
    const params = new HttpParams().set('userId', userDetails.userId);
    params.set('userType', userDetails.userType);
    this.http.patch(this.ROOT_URL + 'user/usertype/' + userDetails.userId + '/' + userDetails.userType, {}).subscribe();
  }

  /**
   * Update users' expiration date
   */
  changeUserExpiration(userDetails) {

    // {userId}/{numDays}
    // console.log(userDetails.userId)
    // console.log(userDetails.nrDays)
    const params = new HttpParams().set('userId', userDetails.userId);
    params.set('userType', userDetails.nrDays);
    this.http.patch(this.ROOT_URL + 'user/expiration/' + userDetails.userId + '/' + userDetails.nrDays, {}).subscribe();
  }

  getUserJSONArray(): Observable<User []> {
    console.log('getting JSON user');
    return this.http.get<User []>(this.JSON_URL + 'user.json');
  }

}
