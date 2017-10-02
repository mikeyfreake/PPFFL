import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TeamStats } from './team-stats.model';
import { TeamStatsPopupService } from './team-stats-popup.service';
import { TeamStatsService } from './team-stats.service';
import { Team, TeamService } from '../team';
import { Season, SeasonService } from '../season';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-team-stats-dialog',
    templateUrl: './team-stats-dialog.component.html'
})
export class TeamStatsDialogComponent implements OnInit {

    teamStats: TeamStats;
    isSaving: boolean;

    teams: Team[];

    seasons: Season[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private teamStatsService: TeamStatsService,
        private teamService: TeamService,
        private seasonService: SeasonService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.teamService.query()
            .subscribe((res: ResponseWrapper) => { this.teams = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.seasonService.query()
            .subscribe((res: ResponseWrapper) => { this.seasons = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.teamStats.id !== undefined) {
            this.subscribeToSaveResponse(
                this.teamStatsService.update(this.teamStats));
        } else {
            this.subscribeToSaveResponse(
                this.teamStatsService.create(this.teamStats));
        }
    }

    private subscribeToSaveResponse(result: Observable<TeamStats>) {
        result.subscribe((res: TeamStats) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: TeamStats) {
        this.eventManager.broadcast({ name: 'teamStatsListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackTeamById(index: number, item: Team) {
        return item.id;
    }

    trackSeasonById(index: number, item: Season) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-team-stats-popup',
    template: ''
})
export class TeamStatsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private teamStatsPopupService: TeamStatsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.teamStatsPopupService
                    .open(TeamStatsDialogComponent as Component, params['id']);
            } else {
                this.teamStatsPopupService
                    .open(TeamStatsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
