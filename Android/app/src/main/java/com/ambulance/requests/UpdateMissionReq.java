package com.ambulance.requests;

import com.ambulance.entities.MissionStatus;

public class UpdateMissionReq {
    Long id;
    MissionStatus status;

    public UpdateMissionReq() {
    }

    public UpdateMissionReq(Long id, MissionStatus status) {
        this.id = id;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MissionStatus getStatus() {
        return status;
    }

    public void setStatus(MissionStatus status) {
        this.status = status;
    }
}
