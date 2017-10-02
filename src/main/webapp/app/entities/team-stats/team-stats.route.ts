import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { TeamStatsComponent } from './team-stats.component';
import { TeamStatsDetailComponent } from './team-stats-detail.component';
import { TeamStatsPopupComponent } from './team-stats-dialog.component';
import { TeamStatsDeletePopupComponent } from './team-stats-delete-dialog.component';

export const teamStatsRoute: Routes = [
    {
        path: 'team-stats',
        component: TeamStatsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'scraperApp.teamStats.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'team-stats/:id',
        component: TeamStatsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'scraperApp.teamStats.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const teamStatsPopupRoute: Routes = [
    {
        path: 'team-stats-new',
        component: TeamStatsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'scraperApp.teamStats.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'team-stats/:id/edit',
        component: TeamStatsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'scraperApp.teamStats.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'team-stats/:id/delete',
        component: TeamStatsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'scraperApp.teamStats.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
