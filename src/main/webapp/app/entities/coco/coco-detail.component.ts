import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICOCO } from 'app/shared/model/coco.model';

@Component({
  selector: 'jhi-coco-detail',
  templateUrl: './coco-detail.component.html'
})
export class COCODetailComponent implements OnInit {
  cOCO: ICOCO;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ cOCO }) => {
      this.cOCO = cOCO;
    });
  }

  previousState() {
    window.history.back();
  }
}
