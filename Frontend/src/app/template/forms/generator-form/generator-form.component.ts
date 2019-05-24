import { Component, OnInit } from '@angular/core';
import { RequestsService } from 'src/app/requests.service';
import { Generator } from 'src/app/pages/generators/generators.component';

@Component({
  selector: 'app-generator-form',
  templateUrl: './generator-form.component.html',
  styleUrls: ['./generator-form.component.css']
})
export class GeneratorFormComponent implements OnInit {

  service: RequestsService
  gen = new Generator();

  constructor(service: RequestsService) { 
    this.service = service;
  }

  ngOnInit() {
  }

  add_generator(generatorForm){
    console.log(this.gen);
   this.service.add_generator(this.gen);
  }

}
