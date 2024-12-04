import { User } from "./user";
import { Hospital } from "./hospital";

export enum MissionStatus {
  PENDING = "PENDING",
  IN_PROGRESS = "IN_PROGRESS",
  COMPLETED = "COMPLETED",
  CANCELLED = "CANCELLED"
}

export interface Mission {
  id: number;
  status: MissionStatus;
  address: string;
  driver: User;
  dispatcher: User;
  hospital: Hospital;
}

