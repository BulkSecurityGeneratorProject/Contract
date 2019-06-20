import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRegistrationType } from 'app/shared/model/registration-type.model';

@Component({
  selector: 'jhi-registration-type-detail',
  templateUrl: './registration-type-detail.component.html'
})
export class RegistrationTypeDetailComponent implements OnInit {
  registrationType: IRegistrationType;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ registrationType }) => {
      this.registrationType = registrationType;
    });
  }

  previousState() {
    window.history.back();
  }
}
