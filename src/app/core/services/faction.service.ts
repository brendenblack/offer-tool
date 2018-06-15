import { Injectable, OnInit } from "@angular/core";
import { Observable, of } from "rxjs";
import { Faction } from "../models";

@Injectable()
export class FactionService implements OnInit {

    private factions: Faction[];

    ngOnInit(): void {
        this.factions = [];

        let sur: Faction = new Faction();
        sur.id = "SUR";
        sur.name = "Survivors";
        this.factions.push(sur);

        let cor: Faction = new Faction();
        cor.id = "COR";
        cor.name = "Corpus";
        this.factions.push(cor);

        let sen = new Faction();
        sen.id = "SEN";
        sen.name = "Sentinels";
        this.factions.push(sen);
    }

    getAll() : Observable<Faction[]> {
        return of(this.factions);
    }

    get(id:String) : Observable<Faction> {
        let faction = this.factions.find(f => f.id == id);
        return of(faction);
    }
}