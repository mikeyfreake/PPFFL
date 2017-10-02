import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { TeamStats } from './team-stats.model';
import { TeamStatsService } from './team-stats.service';

@Component({
    selector: 'jhi-team-stats-detail',
    templateUrl: './team-stats-detail.component.html'
})
export class TeamStatsDetailComponent implements OnInit, OnDestroy {

    teamStats: TeamStats;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private teamStatsService: TeamStatsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTeamStats();
    }

    load(id) {
        this.teamStatsService.find(id).subscribe((teamStats) => {
            this.teamStats = teamStats;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTeamStats() {
        this.eventSubscriber = this.eventManager.subscribe(
            'teamStatsListModification',
            (response) => this.load(this.teamStats.id)
        );
    }
}
