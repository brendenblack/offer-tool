import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UnitsListComponent } from './units-list/units-list.component';
import { OfferCreationComponent } from './offer-creation.component';
import { OfferCreationRoutingModule } from './offer-creation-routing.module';
import { UnitService } from '../core/services/unit.service';

@NgModule({
  imports: [
    CommonModule,
    OfferCreationRoutingModule
  ],
  declarations: [
    UnitsListComponent, 
    OfferCreationComponent
  ],
  providers: [
    UnitService
  ]
})
export class OfferCreationModule { 

}
