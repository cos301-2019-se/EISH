import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import 'rxjs/add/operator/map';
import { map, mergeMap, switchMap, catchError } from 'rxjs/operators';
import { forkJoin, pipe, Subject, Observable, Subscription} from 'rxjs';
import { ifError } from 'assert';
import { errorHandler } from '@angular/platform-browser/src/browser';
import { error } from 'util';


@Injectable({
  providedIn: 'root'
})
export class UserAccessControlService {
  /**
   * Class executes HTTP requests to EISHMS API
   */

 /* Variables: */
  ROOT_URL = 'http://localhost:8080/api/';
  data:any;

  constructor( private http: HttpClient) { }

  /**
   * Sends username and password to endpoint and receives JWT upon success
   * Uses session storage to store username and JWT
   * POST Request
   * exposed endpoint:
   * @param userCredentials: json of user form data
   * @returns Boolean 
   */
  authenticateUser(userCredentials, loginForm){
   let parameter = {"user_id": 1}
    
     return this.http.post(this.ROOT_URL+'auth/login/',parameter).pipe(
      map( response => {
            this.data =  response[0],
            sessionStorage.setItem('accessToken', this.data.accessToken),
            sessionStorage.setItem('userName' , userCredentials.userEmail);
            loginForm.switcher();

      }/*, catchError(error => 
            loginForm.error()
          )*/
      )).subscribe(error => (loginForm.error()));
    
  }

  /**
   * Checks session storage to see if user is currently logged in  
   * @returns Boolean
   */
  isUserLoggedIn(): Boolean {
    if(sessionStorage == null )
      return false;
    else
      return true;
  }
 
  /**
   * Clears session storage, invalidates JWT
   * Route to login page
   * PUT Request
   */
  userLogOut(): void{
    sessionStorage.clear(); //window.sessionStorage.clear();
    //load login page

  }

  /**
   * Sends user information to API
   * PUT Request
   * exposed endpoint:
   * @param userCredentials: json of user form data
   * @returns Boolean
   */
  changeCredentials(userCredentials, credentialInstance): any {
    let parameter = {"user_id": 1}
    this.http.put(this.ROOT_URL + 'auth/login', parameter).subscribe(
     ( res: Response) =>{ 
       if(res.ok) 
        credentialInstance.route('dashboard')
      else 
     credentialInstance.route('register?regType=Change'); // error message?
    });
  }

  /**'
   * Sends user information to API, receives JWT upon success
   * POST Request
   * exposed endpoint:
   * @param credential Object
   * @returns
   */
  registerUser(userCredentials, registerInstace):any{
    let parameter = {"user_id": 1}
    return this.http.post(this.ROOT_URL+'auth/login/',parameter).pipe(
      map( 
        response => {
            this.data =  response[0],
            sessionStorage.clear()
            registerInstace.route('login')
          }
      )).subscribe();
  }

  /**
   * Authenticates given key and upon successful validation receives JWT
   * POST Request
   * exposed endpoint:
   * @param
   * @returns
   */
  authenticateKey(key, keyInstance): any{
    //key.userKey ??
    let parameter = {"user_id": 1}
    return this.http.post(this.ROOT_URL+'auth/login/',parameter).pipe(
      map(
        response => {
            this.data =  response[0],
            sessionStorage.setItem('accessToken', this.data.accessToken),
            sessionStorage.setItem('key', key);
            keyInstance.route();
          }
      )).subscribe();
   return true;
  }

}
