import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GeneratorModalComponent } from './generator-modal.component';

describe('GeneratorModalComponent', () => {
  let component: GeneratorModalComponent;
  let fixture: ComponentFixture<GeneratorModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GeneratorModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GeneratorModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
