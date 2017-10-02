import { BaseEntity } from './../../shared';

export class Team implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public owner?: string,
        public teamStats?: BaseEntity[],
    ) {
    }
}
