import { BaseEntity } from './../../shared';

export class Season implements BaseEntity {
    constructor(
        public id?: number,
        public year?: number,
        public teamStats?: BaseEntity[],
        public leagueId?: number,
    ) {
    }
}
