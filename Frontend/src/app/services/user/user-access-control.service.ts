import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import 'rxjs/add/operator/map';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserAccessControlService {
  /**
   * Class executes HTTP requests to EISHMS API
   */

 /* Variables: */
  ROOT_URL = 'http://localhost:8080/api/';
 
  constructor( private http: HttpClient) { }

  /**
   * Sends username and password to endpoint and receives JWT upon success
   * Uses session storage to store username and JWT
   * POST Request
   * exposed endpoint:
   * @param userCredentials: json of user form data
   * @returns Boolean 
   */
 
  authenticateUser(userCredentials): Boolean{
    //receives json: tokenType, accessToken
    //sessionStorage.setItem('username', userCredentials.username)
    //sessionStorage.setItem('token', map.data.accessToken)

    let jsondata: any;

    this.http.post(this.ROOT_URL+'auth/login', userCredentials).pipe( 
      map(
      (response: any) => {jsondata =  response.json()
      },err =>{
        //handle error
        //console.log(err)
        //send false
        return false;
      }
    ));

    sessionStorage.setItem('userName', userCredentials.userName);
    sessionStorage.setItem('token', jsondata.accessToken)
    //console.log(jsondata);
    return true;

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
  changeCredentials(userCredentials): Boolean {
    
    this.http.put(this.ROOT_URL + 'user', userCredentials).pipe(
    //error, return false
      
    );
    return true;
  }

  /**'
   * Sends user information to API, receives JWT upon success
   * POST Request
   * exposed endpoint:
   * @param credential Object
   * @returns
   */
  registerUser(userCredentials): Boolean{
    let jsondata: any
    this.http.post(this.ROOT_URL + 'user', userCredentials ).pipe(
      map(
        (response: any) => {jsondata =  response.json()
        }, err =>{
          //handle error
          //send false
          return false;

      })
    );

    sessionStorage.setItem('userName', userCredentials.userName);
    sessionStorage.setItem('token', jsondata.accessToken)
    //console.log(jsondata);
    return true;
  }

  /**
   * Authenticates given key and upon successful validation receives JWT
   * POST Request
   * exposed endpoint:
   * @param
   * @returns
   */
  authenticateKey(key): Boolean{
    let jsondata: any
    this.http.post(this.ROOT_URL + '', key).pipe(
      map(
        (response: any) => {jsondata =  response.json()
        }, err =>{
          //handle error
          //send false
          return false;
        }
      )
    );
   
    //sessionStorage.setItem(, jsondata.accessToken);
    return true;
  }

}
