import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserAccessControlService {

  /**
   * Class executes HTTP requests to EISHMS API
   */

   /* Variables: */

  constructor() { }

  /**
   * Sends username and password to endpoint and receives JWT upon success
   * Uses session storage to store username and JWT
   * POST Request
   * exposed endpoint:
   * @param
   * @returns
   */
  authenticateUser(){

  }

  /**
   * Checks session storage to see if user is currently logged in  
   * @returns boolean
   */
  isUserLoggedIn(){

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

  }

  /**
   * Sends user information to API
   * PUT Request
   * exposed endpoint:
   * @param credential Object
   * @returns
   */
  changeCredentials(){

  }

  /**'
   * Sends user information to API, receives JWT upon success
   * POST Request
   * exposed endpoint:
   * @param credential Object
   * @returns
   */
  registerUser(){

  }

  /**
   * Authenticates given key and upon successful validation receives JWT
   * POST Request
   * exposed endpoint:
   * @param
   * @returns
   */
  authenticateKey(){

  }
}
