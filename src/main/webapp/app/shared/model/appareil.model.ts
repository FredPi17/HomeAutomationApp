export interface IAppareil {
  id?: number;
  name?: string;
  type?: number;
  protocol?: number;
  piece?: number;
}

export class Appareil implements IAppareil {
  constructor(public id?: number, public name?: string, public type?: number, public protocol?: number, public piece?: number) {}
}
