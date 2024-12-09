import { User } from "./user";
import { Hospital } from "./hospital";

export enum MissionStatus {
  PENDING = "PENDING",
  IN_PROGRESS = "IN_PROGRESS",
  COMPLETED = "COMPLETED",
  CANCELLED = "CANCELLED",
  ASSIGNED = "ASSIGNED"
}

export interface Mission {
  id: number;
  status: MissionStatus;
  latitude: number;
  longitude: number;
  driver: number;
  dispatcher: number;
  hospital: number;
}

