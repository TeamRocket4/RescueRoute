export enum Role {
  DISPATCHER = "DISPATCHER",
  DRIVER = "DRIVER"
}

export enum Status {
  STANDBY = "STANDBY",
  BUSY = "BUSY",
  OUTOFSERVICE = "OUTOFSERVICE"
}

export interface User {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  role: Role;
  birthDate: string;
  status: Status;
}

