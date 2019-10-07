import { TestBed } from '@angular/core/testing';

import { GeneratorService } from './generator.service';
import { BrowserDynamicTestingModule, platformBrowserDynamicTesting } from '@angular/platform-browser-dynamic/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('GeneratorService', () => {
  beforeEach(() => {
    TestBed.resetTestEnvironment();
    TestBed.initTestEnvironment(BrowserDynamicTestingModule, platformBrowserDynamicTesting());

    TestBed.configureTestingModule({
          imports: [HttpClientTestingModule],
          providers: [GeneratorService]
        });
  });


  it('should be created', () => {
    const service: GeneratorService = TestBed.get(GeneratorService);
    expect(service).toBeTruthy();
  });
});
