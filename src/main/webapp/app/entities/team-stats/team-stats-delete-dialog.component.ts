import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TeamStats } from './team-stats.model';
import { TeamStatsPopupService } from './team-stats-popup.service';
import { TeamStatsService } from './team-stats.service';

@Component({
    selector: 'jhi-team-stats-delete-dialog',
    templateUrl: './team-stats-delete-dialog.component.html'
})
export class TeamStatsDeleteDialogComponent {

    teamStats: TeamStats;

    constructor(
        private teamStatsService: TeamStatsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.teamStatsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'teamStatsListModification',
                content: 'Deleted an teamStats'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-team-stats-delete-popup',
    template: ''
})
export class TeamStatsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private teamStatsPopupService: TeamStatsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.teamStatsPopupService
                .open(TeamStatsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
