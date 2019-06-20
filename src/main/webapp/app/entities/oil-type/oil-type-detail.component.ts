import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOilType } from 'app/shared/model/oil-type.model';

@Component({
  selector: 'jhi-oil-type-detail',
  templateUrl: './oil-type-detail.component.html'
})
export class OilTypeDetailComponent implements OnInit {
  oilType: IOilType;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ oilType }) => {
      this.oilType = oilType;
    });
  }

  previousState() {
    window.history.back();
  }
}
