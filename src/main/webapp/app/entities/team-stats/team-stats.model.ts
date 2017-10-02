import { BaseEntity } from './../../shared';

export class TeamStats implements BaseEntity {
    constructor(
        public id?: number,
        public rank?: number,
        public wins?: number,
        public losses?: number,
        public ties?: number,
        public pointsFor?: number,
        public pointsAgainst?: number,
        public pointsForPerGame?: number,
        public pointsAgainstPerGame?: number,
        public pointsDiffPerGame?: number,
        public teamId?: number,
        public seasonId?: number,
    ) {
    }
}
