import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { TeamStats } from './team-stats.model';
import { TeamStatsService } from './team-stats.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-team-stats',
    templateUrl: './team-stats.component.html'
})
export class TeamStatsComponent implements OnInit, OnDestroy {
teamStats: TeamStats[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private teamStatsService: TeamStatsService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.teamStatsService.query().subscribe(
            (res: ResponseWrapper) => {
                this.teamStats = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInTeamStats();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: TeamStats) {
        return item.id;
    }
    registerChangeInTeamStats() {
        this.eventSubscriber = this.eventManager.subscribe('teamStatsListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
