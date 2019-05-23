import { Component, OnInit } from '@angular/core';

export class Generator{
  generator_name: string
  generator_topic: string
  min_capacity_watts: number
  max_capacity_watts: number
  generator_type: string

  constructor(){}
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
