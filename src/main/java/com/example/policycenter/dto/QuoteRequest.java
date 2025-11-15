package com.example.policycenter.dto;

public class QuoteRequest {

    private String insuredName;
    private String vehicleRegNo;
    private String vehicleMake;
    private String vehicleModel;
    private int vehicleYear;

    public String getInsuredName() { return insuredName; }
    public void setInsuredName(String insuredName) { this.insuredName = insuredName; }

    public String getVehicleRegNo() { return vehicleRegNo; }
    public void setVehicleRegNo(String vehicleRegNo) { this.vehicleRegNo = vehicleRegNo; }

    public String getVehicleMake() { return vehicleMake; }
    public void setVehicleMake(String vehicleMake) { this.vehicleMake = vehicleMake; }

    public String getVehicleModel() { return vehicleModel; }
    public void setVehicleModel(String vehicleModel) { this.vehicleModel = vehicleModel; }

    public int getVehicleYear() { return vehicleYear; }
    public void setVehicleYear(int vehicleYear) { this.vehicleYear = vehicleYear; }
}
