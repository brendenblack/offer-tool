import { NgModule } from '@angular/core';
import { Routes, RouterModule, PreloadAllModules } from '@angular/router';

const routes: Routes = [
    // {
    //   path: '',
    //   component: HomeComponent
    // },
    {
      path: '',
      loadChildren: './offer-creation/offer-creation.module#OfferCreationModule'
    }];

@NgModule({
    imports: [RouterModule.forRoot(routes, {
        // preload all modules; optionally we could
        // implement a custom preloading strategy for just some
        // of the modules
        preloadingStrategy: PreloadAllModules
    })],
    exports: [RouterModule]
    })
    export class AppRoutingModule {}
      