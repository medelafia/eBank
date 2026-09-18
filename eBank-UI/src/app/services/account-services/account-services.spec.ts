import { TestBed } from '@angular/core/testing';

import { AccountServices } from './account-services';

describe('AccountServices', () => {
  let service: AccountServices;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AccountServices);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
