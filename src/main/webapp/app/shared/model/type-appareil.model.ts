export interface ITypeAppareil {
  id?: number;
  name?: string;
}

export class TypeAppareil implements ITypeAppareil {
  constructor(public id?: number, public name?: string) {}
}
