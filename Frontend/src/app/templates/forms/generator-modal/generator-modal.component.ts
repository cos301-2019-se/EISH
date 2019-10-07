import { Component, OnInit, inject } from '@angular/core';
import {MatDialogRef} from '@angular/material';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { GeneratorService } from 'src/app/services/generators/generator.service';
import {map, startWith} from 'rxjs/operators';
@Component({
  selector: 'app-generator-modal',
  templateUrl: './generator-modal.component.html',
  styleUrls: ['./generator-modal.component.css']
})
export class GeneratorModalComponent implements OnInit {

  /**
   * Variables:
   */
  addGeneratorForm: FormGroup;
  selected: any;

  constructor(private generatorService: GeneratorService,
              private fb: FormBuilder, private dialogRef: MatDialogRef<GeneratorModalComponent>) { }

  ngOnInit() {
    this.addGeneratorForm = this.fb.group({
      generatorName: [null, [Validators.required]],
      generatorURL: [null, [Validators.required]],
      generatorPriorityType: [null, [Validators.required]]
    });
  }

  /**
   *  Submits form to add a power generator to system
   */
  addGenerator() {
    if (this.addGeneratorForm.invalid) {
      return;
    } else {
      const generator = {
        generatorName: this.addGeneratorForm.get('generatorName').value,
        generatorUrl: this.addGeneratorForm.get('generatorURL').value, // small case url?
        generatorPriorityType: this.addGeneratorForm.get('generatorPriorityType').value,
        // tslint:disable-next-line: quotemark
        generatorStates: ["ONLINE", "OFFLINE"]
      };
      console.log(generator);
      this.generatorService.addPowerGenerator(generator);
      this.generatorService.getAllPowerGenerators().pipe(
        map( response => {
          console.log(response);
        })
      ).subscribe();
      this.dialogRef.close(this.addGeneratorForm.value);
    }
  }
}
