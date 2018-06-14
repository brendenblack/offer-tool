import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Unit } from "../models";

@Injectable()
export class UnitService {
    
    getAll(): Observable<Unit[]> {
        return Observable.create()
    }
}