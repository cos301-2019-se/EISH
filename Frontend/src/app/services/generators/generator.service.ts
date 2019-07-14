import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { Generator } from 'src/app/models/generator-model';

@Injectable({
  providedIn: 'root'
})
export class GeneratorService {

  /**
   * Variables:
   */
  ROOT_URL = 'http://192.168.8.102:8080/api/';

  constructor(private http: HttpClient) { }
  /**
   * Retrieves current battery percentage
   * GET Request
   * @return returns an observable
   */
  getBatteryPercentage(): Observable<{}> {
    return this.http.get(this.ROOT_URL + '');
  }

  /**
   * Retrieve a list of all generators
   * GET Request
   * @returns Observable array of Generator Objects
   */
  getAllPowerGenerators(): Observable<Generator[]> {
    return this.http.get<Generator[]>(this.ROOT_URL + '');
  }

  /**
   * Retrieve a given generator
   * GET Request
   * @param generatorId The id used to find that specific generator.
   * @returns Observable array of Generator Objects
   */
  getPowerGenerator(generatorId): Observable<Generator[]> {
    const params = new HttpParams().set('generatorId', generatorId);
    return this.http.get<Generator[]>(this.ROOT_URL + '', {params});
  }
  /**
   * Add new power generator to system
   * POST Request
   * @param generatorForm Information about the generator given by the user.
   */
  addPowerGenerator(generatorForm) {
    this.http.post(this.ROOT_URL + '', generatorForm );
  }

  /**
   * Change generator properties
   * PUT Request
   */
  editPowerGenerator(generatorForm) {
    this.http.put(this.ROOT_URL + '', generatorForm);
  }

  /**
   * Remove specfic power generator from system
   * DELETE Request
   * @param generatorId The id used to find that specific generator.
   */
  removePowerGenerator(generatorId) {
    this.http.delete(this.ROOT_URL + '', generatorId);
  }
}


