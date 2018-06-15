import { Injectable } from "@angular/core";
import { Observable, of } from "rxjs";
import { Unit, Faction } from "../models";


@Injectable()
export class UnitService {
    constructor() {}

        
    getAll(): Observable<Unit[]> {
        let sur: Faction = new Faction();
        sur.id = "SUR";
        sur.name = "Survivors";
        
        let cor: Faction = new Faction();
        cor.id = "COR";
        cor.name = "Corpus";

        let sen = new Faction();
        sen.id = "SEN";
        sen.name = "Sentinels";

        let units : Unit[] = [];

        let sphinx = new Unit();
        sphinx.faction = sen;
        sphinx.name = "Sphinx";
        sphinx.sku = "maxbuildableunitid360";
        sphinx.displayCode = "sphinxunlocked";
        units.push(sphinx);

        return of(units);
    }
}