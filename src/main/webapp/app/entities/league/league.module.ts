import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ScraperSharedModule } from '../../shared';
import {
    LeagueService,
    LeaguePopupService,
    LeagueComponent,
    LeagueDetailComponent,
    LeagueDialogComponent,
    LeaguePopupComponent,
    LeagueDeletePopupComponent,
    LeagueDeleteDialogComponent,
    leagueRoute,
    leaguePopupRoute,
} from './';

const ENTITY_STATES = [
    ...leagueRoute,
    ...leaguePopupRoute,
];

@NgModule({
    imports: [
        ScraperSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        LeagueComponent,
        LeagueDetailComponent,
        LeagueDialogComponent,
        LeagueDeleteDialogComponent,
        LeaguePopupComponent,
        LeagueDeletePopupComponent,
    ],
    entryComponents: [
        LeagueComponent,
        LeagueDialogComponent,
        LeaguePopupComponent,
        LeagueDeleteDialogComponent,
        LeagueDeletePopupComponent,
    ],
    providers: [
        LeagueService,
        LeaguePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ScraperLeagueModule {}
