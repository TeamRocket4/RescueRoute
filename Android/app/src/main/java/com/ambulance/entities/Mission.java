package com.ambulance.entities;

public class Mission {
    private Long id;

    private MissionStatus status;

    private Hospital hospital;

    private double latitude;

    private double longitude;

    public Mission() {
    }

    public Mission(Long id, MissionStatus status, Hospital hospital, double latitude, double longitude) {
        this.id = id;
        this.status = status;
        this.hospital = hospital;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
