import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UnitsListComponent } from './units-list/units-list.component';
import { OfferCreationComponent } from './offer-creation.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [UnitsListComponent, OfferCreationComponent]
})
export class OfferCreationModule { }
