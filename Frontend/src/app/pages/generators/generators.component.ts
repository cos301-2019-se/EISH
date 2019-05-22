import { Component, OnInit } from '@angular/core';

export interface Generator{
  generator_name: string,
  topic: string,
  min_capacity: DoubleRange, 
  max_capacity: DoubleRange,
  generator_type: string
 }

@Component({
  selector: 'app-generators',
  templateUrl: './generators.component.html',
  styleUrls: ['./generators.component.css']
})
export class GeneratorsComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
