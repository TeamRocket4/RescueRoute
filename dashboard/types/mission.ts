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
  _links: Links;
}

type Link = {
  href: string;
};

type Links = {
  self: Link;
  mission: Link;
  hospital: Link;
  dispatcher: Link;
  driver: Link;
};

