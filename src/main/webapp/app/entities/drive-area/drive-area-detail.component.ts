import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDriveArea } from 'app/shared/model/drive-area.model';

@Component({
  selector: 'jhi-drive-area-detail',
  templateUrl: './drive-area-detail.component.html'
})
export class DriveAreaDetailComponent implements OnInit {
  driveArea: IDriveArea;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ driveArea }) => {
      this.driveArea = driveArea;
    });
  }

  previousState() {
    window.history.back();
  }
}
