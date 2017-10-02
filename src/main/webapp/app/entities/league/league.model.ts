import { BaseEntity } from './../../shared';

export const enum FantasyProvider {
    'ESPN',
    'NFL',
    'YAHOO'
}

export class League implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public importedFrom?: FantasyProvider,
        public constitution?: string,
        public seasons?: BaseEntity[],
    ) {
    }
}
