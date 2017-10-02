import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { League } from './league.model';
import { LeaguePopupService } from './league-popup.service';
import { LeagueService } from './league.service';

@Component({
    selector: 'jhi-league-dialog',
    templateUrl: './league-dialog.component.html'
})
export class LeagueDialogComponent implements OnInit {

    league: League;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private leagueService: LeagueService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.league.id !== undefined) {
            this.subscribeToSaveResponse(
                this.leagueService.update(this.league));
        } else {
            this.subscribeToSaveResponse(
                this.leagueService.create(this.league));
        }
    }

    private subscribeToSaveResponse(result: Observable<League>) {
        result.subscribe((res: League) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: League) {
        this.eventManager.broadcast({ name: 'leagueListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-league-popup',
    template: ''
})
export class LeaguePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private leaguePopupService: LeaguePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.leaguePopupService
                    .open(LeagueDialogComponent as Component, params['id']);
            } else {
                this.leaguePopupService
                    .open(LeagueDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
