import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { INationality } from 'app/shared/model/nationality.model';
import { AccountService } from 'app/core';
import { NationalityService } from './nationality.service';

@Component({
  selector: 'jhi-nationality',
  templateUrl: './nationality.component.html'
})
export class NationalityComponent implements OnInit, OnDestroy {
  nationalities: INationality[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected nationalityService: NationalityService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.nationalityService
      .query()
      .pipe(
        filter((res: HttpResponse<INationality[]>) => res.ok),
        map((res: HttpResponse<INationality[]>) => res.body)
      )
      .subscribe(
        (res: INationality[]) => {
          this.nationalities = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInNationalities();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: INationality) {
    return item.id;
  }

  registerChangeInNationalities() {
    this.eventSubscriber = this.eventManager.subscribe('nationalityListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
