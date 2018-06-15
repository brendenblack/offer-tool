import { Component, OnInit } from '@angular/core';
import { UnitService } from '../../core/services/unit.service';
import { Unit } from '../../core/models';

@Component({
  selector: 'app-units-list',
  templateUrl: './units-list.component.html',
  styleUrls: ['./units-list.component.css']
})
export class UnitsListComponent implements OnInit {

  constructor(private unitService:UnitService) { }

  units : Unit[];

  ngOnInit() {
    this.unitService.getAll().subscribe(units => this.units = units);
  }

}
