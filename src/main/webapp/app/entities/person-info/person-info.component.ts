import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPersonInfo } from 'app/shared/model/person-info.model';
import { AccountService } from 'app/core';
import { PersonInfoService } from './person-info.service';

@Component({
  selector: 'jhi-person-info',
  templateUrl: './person-info.component.html'
})
export class PersonInfoComponent implements OnInit, OnDestroy {
  personInfos: IPersonInfo[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected personInfoService: PersonInfoService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.personInfoService
      .query()
      .pipe(
        filter((res: HttpResponse<IPersonInfo[]>) => res.ok),
        map((res: HttpResponse<IPersonInfo[]>) => res.body)
      )
      .subscribe(
        (res: IPersonInfo[]) => {
          this.personInfos = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPersonInfos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPersonInfo) {
    return item.id;
  }

  registerChangeInPersonInfos() {
    this.eventSubscriber = this.eventManager.subscribe('personInfoListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
