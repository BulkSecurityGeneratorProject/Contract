import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPersonInfo } from 'app/shared/model/person-info.model';
import { PersonInfoService } from './person-info.service';

@Component({
  selector: 'jhi-person-info-delete-dialog',
  templateUrl: './person-info-delete-dialog.component.html'
})
export class PersonInfoDeleteDialogComponent {
  personInfo: IPersonInfo;

  constructor(
    protected personInfoService: PersonInfoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.personInfoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'personInfoListModification',
        content: 'Deleted an personInfo'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-person-info-delete-popup',
  template: ''
})
export class PersonInfoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ personInfo }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PersonInfoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.personInfo = personInfo;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/person-info', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/person-info', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
