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
  statusVariable : Boolean


  constructor( private http: HttpClient) { }

  /**
   * Sends username and password to endpoint and receives JWT upon success
   * Uses session storage to store username and JWT
   * POST Request
   * exposed endpoint:
   * @param userCredentials: json of user form data
   * @returns Boolean 
   */
  authenticateUser(userCredentials){
    //receives json: tokenType, accessToken
    //sessionStorage.setItem('username', userCredentials.username)
    //sessionStorage.setItem('token', map.data.accessToken)
    
    let parameter = {"user_id": 1}
    
     return this.http.post(this.ROOT_URL+'auth/login/', parameter).pipe(
      map( response => {
            this.statusVariable =  false;
            this.data =  response[0],
            console.log(this.data),
            sessionStorage.setItem('accessToken', this.data.accessToken),
            sessionStorage.setItem('userName' , userCredentials.userEmail);
            this.statusVariable = true;
            this.quickDisplay();
            return true;

          }
      )).subscribe({
        next: (res) => {console.log(res); 
          return res},
        complete: () => {console.log('completed')}
      });
    
  }

  quickDisplay(){
    console.log('in QD, data: ' + this.data.accessToken);
    console.log('in QD, token.UN: ' + sessionStorage.getItem('userName'))
    console.log('in QD, token.AT: ' + sessionStorage.getItem('accessToken'))
   
    sessionStorage.clear();
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
  changeCredentials(userCredentials): any {
    
    this.http.put(this.ROOT_URL + 'user', userCredentials).subscribe(
     ( res: Response) =>{ 
       if(res.ok) 
        return true;
      else 
      return false;
    });
  }

  /**'
   * Sends user information to API, receives JWT upon success
   * POST Request
   * exposed endpoint:
   * @param credential Object
   * @returns
   */
  registerUser(userCredentials):any{
    
    this.http.post(this.ROOT_URL+'auth/login/',userCredentials).pipe(
      map(
        (response: Response) =>{
        if(!response.ok)
          return false
        },  
        response => {
            this.data =  response[0],
            sessionStorage.setItem('accessToken', this.data.accessToken),
            sessionStorage.setItem('userName' , userCredentials.userEmail);
          return true;
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
  authenticateKey(key): any{
    //key.userKey
   
    /*this.http.post(this.ROOT_URL+'auth/login/',key).pipe(
      map(
        (response: Response) =>{
          if(!response.ok)
            return false
        },
          response => {
            this.data =  response[0],
            sessionStorage.setItem('accessToken', this.data.accessToken),
            sessionStorage.setItem('key', key);
          return true;
          }
      )).subscribe();*/
   return true;
  }

}
