import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILicenseType } from 'app/shared/model/license-type.model';

@Component({
  selector: 'jhi-license-type-detail',
  templateUrl: './license-type-detail.component.html'
})
export class LicenseTypeDetailComponent implements OnInit {
  licenseType: ILicenseType;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ licenseType }) => {
      this.licenseType = licenseType;
    });
  }

  previousState() {
    window.history.back();
  }
}
