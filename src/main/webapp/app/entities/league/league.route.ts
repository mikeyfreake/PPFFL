import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { LeagueComponent } from './league.component';
import { LeagueDetailComponent } from './league-detail.component';
import { LeaguePopupComponent } from './league-dialog.component';
import { LeagueDeletePopupComponent } from './league-delete-dialog.component';

export const leagueRoute: Routes = [
    {
        path: 'league',
        component: LeagueComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'scraperApp.league.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'league/:id',
        component: LeagueDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'scraperApp.league.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const leaguePopupRoute: Routes = [
    {
        path: 'league-new',
        component: LeaguePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'scraperApp.league.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'league/:id/edit',
        component: LeaguePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'scraperApp.league.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'league/:id/delete',
        component: LeagueDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'scraperApp.league.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
