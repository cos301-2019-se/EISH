import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import 'rxjs/add/operator/map';
import { map, catchError } from 'rxjs/operators';
import {Observable} from 'rxjs';
import { User } from 'src/app/models/user-model';

@Injectable({
  providedIn: 'root'
})
export class UserAccessControlService {
  /**
   * Class executes HTTP requests to EISHMS API
   */

 /* Variables: */
  ROOT_URL = 'http://192.168.8.102:8080/api/';
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
  authenticateUser(userCredentials, loginInstance){
    var loginCred = {username : userCredentials.userName, password : userCredentials.userPassword};
    
     return this.http.post(this.ROOT_URL+'auth/login/',loginCred).pipe(
      map( response => {
            this.data =  response,
            sessionStorage.setItem('accessToken', this.data.accessToken),
            sessionStorage.setItem('userName' , userCredentials.userName);
            
            //check if details are admin if true:
            if (userCredentials.userName == "admin")
              loginInstance.routeToChange();
            else //else go to dashboard
              loginInstance.routeToHomepage();

      }),catchError(error => 
        loginInstance.error())
        ).subscribe();

  }

  /**
   * Checks session storage to see if user is currently logged in  
   * @returns Boolean
   */
  isUserLoggedIn(): Boolean {
    return sessionStorage.getItem("userName") != null;
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
   * Retrieves user information from API
   * GET Request
   * exposed endpoint:
   * @param userCredentials: json of user form data
   * @returns Observerable array of type User
   */
  getUser(): Observable<User> {

    let username = sessionStorage.getItem("userName");
    const params = new HttpParams().set("userName", username);
    return this.http.get<User>(this.ROOT_URL+'user/', { params });   
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
     ( res: Response) =>{ 
       if(res.ok) 
        credentialInstance.route('dashboard', '')
      else 
     credentialInstance.route('register', 'Change'); // error message?
    });
  }

  /**
   * Sends user information to API, receives JWT upon success
   * POST Request
   * exposed endpoint:
   * @param credential Object
   * @returns
   */
  registerUser(userCredentials, registerInstance):any{

    return this.http.post(this.ROOT_URL+'user/',userCredentials).pipe(
      map( 
        response => {
            this.data =  response,
            sessionStorage.clear()
            registerInstance.route('/', '')
          }
      ),catchError(error => 
        registerInstance.error())
        ).subscribe();
  }

  /**
   * Authenticates given key and upon successful validation receives JWT
   * POST Request
   * exposed endpoint:
   * @param
   * @returns
   */
  authenticateKey(keyType, key, keyInstance): any{
    let keyLogin = {username: keyType, password: key.userKey};
    
    return this.http.post(this.ROOT_URL+'auth/login/', keyLogin).pipe(
      map(
        response => {
            this.data =  response,
            sessionStorage.setItem('accessToken', this.data.accessToken),
            sessionStorage.setItem('key', key);
            keyInstance.route();
          }
      ),catchError(error => 
       keyInstance.errorInForm())).subscribe();
  }
  
  removeUser(){
    
  }

}
