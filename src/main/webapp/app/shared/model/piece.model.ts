export interface IPiece {
  id?: number;
  name?: string;
}

export class Piece implements IPiece {
  constructor(public id?: number, public name?: string) {}
}
