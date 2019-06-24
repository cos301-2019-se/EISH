import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs-compat/operator/map';

@Injectable({
  providedIn: 'root'
})
export class UserAccessControlService {
  /**
   * Class executes HTTP requests to EISHMS API
   */

 /* Variables: */
 ROOT_URL = 'http://localhost:8080/api';


  constructor( private http: HttpClient) { }

  /**
   * Sends username and password to endpoint and receives JWT upon success
   * Uses session storage to store username and JWT
   * POST Request
   * exposed endpoint:
   * @param
   * @returns
   */
 
  authenticateUser(userData): Observable<any>{
    //receives json: tokenType, accessToken
    //sessionStorage.setItem('username', userData.username)
    //sessionStorage.setItem('token', map.data.accessToken)
    return this.http.post(this.ROOT_URL+'auth', userData);
  }

  /**
   * Checks session storage to see if user is currently logged in  
   * @returns boolean
   */
  isUserLoggedIn(): boolean{
    if(sessionStorage == null)
      return false;
    else
      return true;
  }

  /**
   * Clears session storage, invalidates JWT
   * Route to login page
   * PUT Request
   * exposed endpoint:
   * @param 
   * @returns
   */
  userLogOut(){
    sessionStorage.clear(); //window.sessionStorage.clear();
    //load login page

  }

  /**
   * Sends user information to API
   * PUT Request
   * exposed endpoint:
   * @param credential Object
   * @returns
   */
  changeCredentials() : Observable<{}> {
    return null;
  }

  /**'
   * Sends user information to API, receives JWT upon success
   * POST Request
   * exposed endpoint:
   * @param credential Object
   * @returns
   */
  registerUser(userCredentials){
    this.http.post(this.ROOT_URL + '', userCredentials )
  }

  /**
   * Authenticates given key and upon successful validation receives JWT
   * POST Request
   * exposed endpoint:
   * @param
   * @returns
   */
  authenticateKey(key){
    this.http.post(this.ROOT_URL + '', key);
  }
}
