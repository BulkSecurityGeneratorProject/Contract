import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IIdType } from 'app/shared/model/id-type.model';
import { AccountService } from 'app/core';
import { IdTypeService } from './id-type.service';

@Component({
  selector: 'jhi-id-type',
  templateUrl: './id-type.component.html'
})
export class IdTypeComponent implements OnInit, OnDestroy {
  idTypes: IIdType[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected idTypeService: IdTypeService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.idTypeService
      .query()
      .pipe(
        filter((res: HttpResponse<IIdType[]>) => res.ok),
        map((res: HttpResponse<IIdType[]>) => res.body)
      )
      .subscribe(
        (res: IIdType[]) => {
          this.idTypes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInIdTypes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IIdType) {
    return item.id;
  }

  registerChangeInIdTypes() {
    this.eventSubscriber = this.eventManager.subscribe('idTypeListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
