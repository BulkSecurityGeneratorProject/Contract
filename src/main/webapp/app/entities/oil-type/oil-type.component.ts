import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IOilType } from 'app/shared/model/oil-type.model';
import { AccountService } from 'app/core';
import { OilTypeService } from './oil-type.service';

@Component({
  selector: 'jhi-oil-type',
  templateUrl: './oil-type.component.html'
})
export class OilTypeComponent implements OnInit, OnDestroy {
  oilTypes: IOilType[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected oilTypeService: OilTypeService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.oilTypeService
      .query()
      .pipe(
        filter((res: HttpResponse<IOilType[]>) => res.ok),
        map((res: HttpResponse<IOilType[]>) => res.body)
      )
      .subscribe(
        (res: IOilType[]) => {
          this.oilTypes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInOilTypes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IOilType) {
    return item.id;
  }

  registerChangeInOilTypes() {
    this.eventSubscriber = this.eventManager.subscribe('oilTypeListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
