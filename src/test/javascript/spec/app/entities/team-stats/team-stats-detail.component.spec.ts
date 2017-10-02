/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ScraperTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TeamStatsDetailComponent } from '../../../../../../main/webapp/app/entities/team-stats/team-stats-detail.component';
import { TeamStatsService } from '../../../../../../main/webapp/app/entities/team-stats/team-stats.service';
import { TeamStats } from '../../../../../../main/webapp/app/entities/team-stats/team-stats.model';

describe('Component Tests', () => {

    describe('TeamStats Management Detail Component', () => {
        let comp: TeamStatsDetailComponent;
        let fixture: ComponentFixture<TeamStatsDetailComponent>;
        let service: TeamStatsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ScraperTestModule],
                declarations: [TeamStatsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TeamStatsService,
                    JhiEventManager
                ]
            }).overrideTemplate(TeamStatsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TeamStatsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TeamStatsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new TeamStats(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.teamStats).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
