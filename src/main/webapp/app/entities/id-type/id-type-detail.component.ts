import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIdType } from 'app/shared/model/id-type.model';

@Component({
  selector: 'jhi-id-type-detail',
  templateUrl: './id-type-detail.component.html'
})
export class IdTypeDetailComponent implements OnInit {
  idType: IIdType;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ idType }) => {
      this.idType = idType;
    });
  }

  previousState() {
    window.history.back();
  }
}
