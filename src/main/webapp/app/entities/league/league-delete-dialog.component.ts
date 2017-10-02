import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { League } from './league.model';
import { LeaguePopupService } from './league-popup.service';
import { LeagueService } from './league.service';

@Component({
    selector: 'jhi-league-delete-dialog',
    templateUrl: './league-delete-dialog.component.html'
})
export class LeagueDeleteDialogComponent {

    league: League;

    constructor(
        private leagueService: LeagueService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.leagueService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'leagueListModification',
                content: 'Deleted an league'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-league-delete-popup',
    template: ''
})
export class LeagueDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private leaguePopupService: LeaguePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.leaguePopupService
                .open(LeagueDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
