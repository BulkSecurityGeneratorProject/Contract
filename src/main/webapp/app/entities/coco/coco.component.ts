import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICOCO } from 'app/shared/model/coco.model';
import { AccountService } from 'app/core';
import { COCOService } from './coco.service';

@Component({
  selector: 'jhi-coco',
  templateUrl: './coco.component.html'
})
export class COCOComponent implements OnInit, OnDestroy {
  cOCOS: ICOCO[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected cOCOService: COCOService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.cOCOService
      .query()
      .pipe(
        filter((res: HttpResponse<ICOCO[]>) => res.ok),
        map((res: HttpResponse<ICOCO[]>) => res.body)
      )
      .subscribe(
        (res: ICOCO[]) => {
          this.cOCOS = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCOCOS();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICOCO) {
    return item.id;
  }

  registerChangeInCOCOS() {
    this.eventSubscriber = this.eventManager.subscribe('cOCOListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
