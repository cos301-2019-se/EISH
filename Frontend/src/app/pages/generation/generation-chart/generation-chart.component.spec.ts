import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GenerationChartComponent } from './generation-chart.component';

describe('GenerationChartComponent', () => {
  let component: GenerationChartComponent;
  let fixture: ComponentFixture<GenerationChartComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GenerationChartComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GenerationChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
