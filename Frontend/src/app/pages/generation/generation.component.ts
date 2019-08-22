import { Component, OnInit, ViewChild } from '@angular/core';
import { GenerationChartComponent } from './generation-chart/generation-chart.component';
import { RxStompService } from '@stomp/ng2-stompjs';
import { Message } from '@stomp/stompjs';
import { GeneratorService } from 'src/app/services/generators/generator.service';

@Component({
  selector: 'app-generation',
  templateUrl: './generation.component.html',
  styleUrls: ['./generation.component.css']
})
export class GenerationComponent implements OnInit {
  @ViewChild(GenerationChartComponent, {static: true}) generationChart: GenerationChartComponent;

  generatorIds = [];
  generators = [];
  startTime = null;
  endTime = null;
  selectedGenerator = 'Home';
  selectedRange = 'lasthour';
  generationType = 'home';
  custom = false;
  topicSubscription = null;
  socketOnline: boolean;
  mode = 'indeterminate';

  constructor(private generationService: GeneratorService, private rxStompService: RxStompService) {
    this.startTime = this.toDateString(new Date());
    this.endTime = this.toDateString(new Date());
    this.socketOnline = false;
  }

  ngOnInit() {
    this.getGenerators();
    // this.generationChart.setHeading('Home Generation');
  }

  private toDateString(date: Date): string {
    return (date.getFullYear().toString() + '-'
       + ('0' + (date.getMonth() + 1)).slice(-2) + '-'
       + ('0' + (date.getDate())).slice(-2))
       + 'T' + date.toTimeString().slice(0, 5);
  }

  private addSeconds(dateTime: string) {
    const DateString = dateTime.replace(/T/g, ' ') + ':00';
    console.log(DateString);
    return DateString;
  }

  getGenerators() {
    this.generationService.getAllGenerators().subscribe(
      (res) => {
        res.forEach((generator) => {
          this.generators.push(generator.generatorName);
          this.generatorIds.push(generator.generatorId);
        });
      }
    );
  }

  isDatesValid(): boolean {
    return false;
  }

  isCustomValid(): boolean {
    return this.selectedGenerator !== '' && this.selectedRange === 'Custom' && this.startTime != null && this.endTime != null;
  }

  getGeneratorGeneration(generatorId, startTimestamp, endTimestamp) {
    this.generationService.getCustomGeneratorGeneration(generatorId, startTimestamp, endTimestamp).subscribe(
      (res) => {
        this.generationChart.addBulkData(res);
      }
    );
  }

  // getHomeConsumption(startTimestamp, endTimestamp) {
  //   this.generationService.getCustomHomeGeneration(startTimestamp, endTimestamp).subscribe(
  //     (res) => {
  //       this.generationChart.addBulkData(res);
  //     }
  //   );
  // }

  getSpecialGeneratorGeneration(generatorId, range) {
    this.generationService.getSpecialGeneratorGeneration(generatorId, range).subscribe(
      (res) => {
        this.generationChart.addBulkData(res);
      }
    );
  }

  // getSpecialHomeGeneration(range) {
  //   this.generationService.getSpecialHomeGeneration(range).subscribe(
  //     (res) => {
  //       this.generationChart.addBulkData(res);
  //     }
  //   );
  // }

  getCurrentGeneratorGeneration(generatorId) {
    if (this.topicSubscription != null) {
      this.topicSubscription.unsubscribe();
    }

    this.generationChart.clearChart();

    this.topicSubscription = this.rxStompService.watch('/generator/' + generatorId + '/generation').subscribe((message: Message) => {
      const generationData = JSON.parse(message.body);
        // add data point to chart
      this.socketOnline = true;
      this.generationChart.addDataPoint(generationData);
      this.generationChart.updateChart();
    });
  }

  // getCurrentHomeGeneration() {
  //   if (this.topicSubscription != null) {
  //     this.topicSubscription.unsubscribe();
  //   }

  //   this.generationChart.clearChart();

  //   this.topicSubscription = this.rxStompService.watch('/home/generation').subscribe((message: Message) => {
  //     const generationData = JSON.parse(message.body);
  //       // add data point to chart
  //     console.log(generationData);
  //     this.generationChart.addDataPoint(generationData);
  //     this.generationChart.updateChart();
  //   });
  // }

  selectGenerator(dropDownValue) {
    console.log(dropDownValue);
    this.selectedGenerator = dropDownValue;
    this.generationType = (dropDownValue === 'Home') ? 'home' : 'generator';
    if (!this.custom) {
      this.updateSpecial();
    } else {
      this.updateCustom();
    }
  }

  selectRange(dropDownValue) {
    this.selectedRange = dropDownValue;
    this.custom = (dropDownValue === 'Custom');
    if (!this.custom) {
      this.updateSpecial();
    } else {
      this.updateCustom();
    }
  }

  setStartDateTime(dateTimeValue) {
    this.startTime = dateTimeValue;
    this.updateCustom();
  }

  setEndDateTime(dateTimeValue) {
    this.endTime = dateTimeValue;
    this.updateCustom();
  }

  getGeneratorId(generatorName) {
    for (let i = 0; i < this.generators.length; i++) {
      if (this.generators[i] === generatorName) {
        return this.generatorIds[i];
      }
    }
  }

  updateCustom() {
    if (this.topicSubscription != null) {
      this.topicSubscription.unsubscribe();
    }
    if (this.selectedGenerator === 'Home') {
      // this.generationChart.setHeading('Home Generation');
      // this.generationChart.setChartHeading('Generation');
      // this.generationChart.setDatasetHeading('Home Generation');
      // this.generationChart.clearChart();
      // if (this.isCustomValid()) {
      //   this.getHomeGeneration(this.addSeconds(this.startTime), this.addSeconds(this.endTime));
      // }
    } else {
      this.generationChart.setHeading('Generator Generation');
      this.generationChart.setChartHeading(this.selectedGenerator + ' Generation');
      this.generationChart.setDatasetHeading('Generator Generation');
      this.generationChart.clearChart();
      if (this.isCustomValid()) {
        this.getGeneratorGeneration(this.getGeneratorId(this.selectedGenerator),
                                    this.addSeconds(this.startTime), this.addSeconds(this.endTime));
      }
    }
  }

  updateSpecial() {
    if (this.selectedRange === 'Current') {
      if (this.generationType === 'home') {
        // this.generationChart.setHeading('Home Generation');
        // this.generationChart.setChartHeading('Generation');
        // this.generationChart.setDatasetHeading('Home Genaration');
        // this.generationChart.clearChart();
        // this.getCurrentHomeGeneration();
      }

      if (this.generationType === 'generator') {
        this.generationChart.setHeading('Generator Generators');
        this.generationChart.setChartHeading(this.selectedGenerator + ' Generation');
        this.generationChart.setDatasetHeading('Generator Generation');
        this.generationChart.clearChart();
        this.getCurrentGeneratorGeneration(this.getGeneratorId(this.selectedGenerator));
      }
    } else {
      if (this.topicSubscription != null) {
        this.topicSubscription.unsubscribe();
      }

      if (this.generationType === 'home') {
        // this.generationChart.setHeading('Home Generation');
        // this.generationChart.setChartHeading('Generation');
        // this.generationChart.setDatasetHeading('Home Generation');
        // this.generationChart.clearChart();
        // this.getSpecialHomeGeneration(this.selectedRange);
      }

      if (this.generationType === 'generator') {
        this.generationChart.setHeading('Generator Generation');
        this.generationChart.setChartHeading(this.selectedGenerator + ' Generation');
        this.generationChart.setDatasetHeading('Generator Generation');
        this.generationChart.clearChart();
        this.getSpecialGeneratorGeneration(this.getGeneratorId(this.selectedGenerator), this.selectedRange);
      }
    }
  }

}
