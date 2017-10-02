import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ScraperSharedModule } from '../../shared';
import {
    TeamStatsService,
    TeamStatsPopupService,
    TeamStatsComponent,
    TeamStatsDetailComponent,
    TeamStatsDialogComponent,
    TeamStatsPopupComponent,
    TeamStatsDeletePopupComponent,
    TeamStatsDeleteDialogComponent,
    teamStatsRoute,
    teamStatsPopupRoute,
} from './';

const ENTITY_STATES = [
    ...teamStatsRoute,
    ...teamStatsPopupRoute,
];

@NgModule({
    imports: [
        ScraperSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TeamStatsComponent,
        TeamStatsDetailComponent,
        TeamStatsDialogComponent,
        TeamStatsDeleteDialogComponent,
        TeamStatsPopupComponent,
        TeamStatsDeletePopupComponent,
    ],
    entryComponents: [
        TeamStatsComponent,
        TeamStatsDialogComponent,
        TeamStatsPopupComponent,
        TeamStatsDeleteDialogComponent,
        TeamStatsDeletePopupComponent,
    ],
    providers: [
        TeamStatsService,
        TeamStatsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ScraperTeamStatsModule {}
