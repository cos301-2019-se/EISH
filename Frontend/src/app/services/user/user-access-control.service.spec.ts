import { TestBed } from '@angular/core/testing';

import { UserAccessControlService } from './user-access-control.service';

describe('UserAccessControlService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: UserAccessControlService = TestBed.get(UserAccessControlService);
    expect(service).toBeTruthy();
  });
});
