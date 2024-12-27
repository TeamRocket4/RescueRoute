import { User } from "./user";
import { Hospital } from "./hospital";

export enum MissionStatus {
  PICKUP = "PICKUP",
  ONROUTETOHOSPITAL = "ONROUTETOHOSPITAL",
  DONE = "DONE",
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

