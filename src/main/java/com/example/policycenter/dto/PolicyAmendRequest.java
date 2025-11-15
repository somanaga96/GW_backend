package com.example.policycenter.dto;

public class PolicyAmendRequest {

    private String vehicleRegNo;
    private String vehicleMake;
    private String vehicleModel;
    private Double premium;

    public String getVehicleRegNo() { return vehicleRegNo; }
    public void setVehicleRegNo(String vehicleRegNo) { this.vehicleRegNo = vehicleRegNo; }

    public String getVehicleMake() { return vehicleMake; }
    public void setVehicleMake(String vehicleMake) { this.vehicleMake = vehicleMake; }

    public String getVehicleModel() { return vehicleModel; }
    public void setVehicleModel(String vehicleModel) { this.vehicleModel = vehicleModel; }

    public Double getPremium() { return premium; }
    public void setPremium(Double premium) { this.premium = premium; }
}
