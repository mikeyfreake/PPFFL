import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ScraperLeagueModule } from './league/league.module';
import { ScraperSeasonModule } from './season/season.module';
import { ScraperTeamModule } from './team/team.module';
import { ScraperTeamStatsModule } from './team-stats/team-stats.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        ScraperLeagueModule,
        ScraperSeasonModule,
        ScraperTeamModule,
        ScraperTeamStatsModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ScraperEntityModule {}
