import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { TeamStats } from './team-stats.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class TeamStatsService {

    private resourceUrl = SERVER_API_URL + 'api/team-stats';

    constructor(private http: Http) { }

    create(teamStats: TeamStats): Observable<TeamStats> {
        const copy = this.convert(teamStats);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(teamStats: TeamStats): Observable<TeamStats> {
        const copy = this.convert(teamStats);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<TeamStats> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to TeamStats.
     */
    private convertItemFromServer(json: any): TeamStats {
        const entity: TeamStats = Object.assign(new TeamStats(), json);
        return entity;
    }

    /**
     * Convert a TeamStats to a JSON which can be sent to the server.
     */
    private convert(teamStats: TeamStats): TeamStats {
        const copy: TeamStats = Object.assign({}, teamStats);
        return copy;
    }
}
