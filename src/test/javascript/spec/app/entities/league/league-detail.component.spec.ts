/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ScraperTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { LeagueDetailComponent } from '../../../../../../main/webapp/app/entities/league/league-detail.component';
import { LeagueService } from '../../../../../../main/webapp/app/entities/league/league.service';
import { League } from '../../../../../../main/webapp/app/entities/league/league.model';

describe('Component Tests', () => {

    describe('League Management Detail Component', () => {
        let comp: LeagueDetailComponent;
        let fixture: ComponentFixture<LeagueDetailComponent>;
        let service: LeagueService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ScraperTestModule],
                declarations: [LeagueDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    LeagueService,
                    JhiEventManager
                ]
            }).overrideTemplate(LeagueDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LeagueDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LeagueService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new League(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.league).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
