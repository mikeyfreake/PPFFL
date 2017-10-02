import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { League } from './league.model';
import { LeagueService } from './league.service';

@Component({
    selector: 'jhi-league-detail',
    templateUrl: './league-detail.component.html'
})
export class LeagueDetailComponent implements OnInit, OnDestroy {

    league: League;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private leagueService: LeagueService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLeagues();
    }

    load(id) {
        this.leagueService.find(id).subscribe((league) => {
            this.league = league;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLeagues() {
        this.eventSubscriber = this.eventManager.subscribe(
            'leagueListModification',
            (response) => this.load(this.league.id)
        );
    }
}
