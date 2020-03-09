export interface IMqttConnection {
  id?: number;
  name?: string;
  urlServeur?: string;
  port?: number;
  username?: string;
  password?: string;
  topic?: string;
}

export class MqttConnection implements IMqttConnection {
  constructor(
    public id?: number,
    public name?: string,
    public urlServeur?: string,
    public port?: number,
    public username?: string,
    public password?: string,
    public topic?: string
  ) {}
}
