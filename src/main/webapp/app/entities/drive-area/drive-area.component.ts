import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDriveArea } from 'app/shared/model/drive-area.model';
import { AccountService } from 'app/core';
import { DriveAreaService } from './drive-area.service';

@Component({
  selector: 'jhi-drive-area',
  templateUrl: './drive-area.component.html'
})
export class DriveAreaComponent implements OnInit, OnDestroy {
  driveAreas: IDriveArea[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected driveAreaService: DriveAreaService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.driveAreaService
      .query()
      .pipe(
        filter((res: HttpResponse<IDriveArea[]>) => res.ok),
        map((res: HttpResponse<IDriveArea[]>) => res.body)
      )
      .subscribe(
        (res: IDriveArea[]) => {
          this.driveAreas = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInDriveAreas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDriveArea) {
    return item.id;
  }

  registerChangeInDriveAreas() {
    this.eventSubscriber = this.eventManager.subscribe('driveAreaListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
