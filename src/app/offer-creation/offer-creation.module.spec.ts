import { OfferCreationModule } from './offer-creation.module';

describe('OfferCreationModule', () => {
  let offerCreationModule: OfferCreationModule;

  beforeEach(() => {
    offerCreationModule = new OfferCreationModule();
  });

  it('should create an instance', () => {
    expect(offerCreationModule).toBeTruthy();
  });
});
