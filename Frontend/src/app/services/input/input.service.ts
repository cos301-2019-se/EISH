import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class InputService {
  /**
   * Class will be used to sanitize and validate input
   */
  constructor() { }

  /**
   * Checks input for malicious attacks against injections
   */
  sanitizeInput() {

  }

  /**
   * Checks input validity
   */
  validateInput() {

  }
}
