import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPersonInfo } from 'app/shared/model/person-info.model';

@Component({
  selector: 'jhi-person-info-detail',
  templateUrl: './person-info-detail.component.html'
})
export class PersonInfoDetailComponent implements OnInit {
  personInfo: IPersonInfo;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ personInfo }) => {
      this.personInfo = personInfo;
    });
  }

  previousState() {
    window.history.back();
  }
}
