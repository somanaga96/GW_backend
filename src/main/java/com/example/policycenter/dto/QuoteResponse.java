package com.example.policycenter.dto;

import java.time.LocalDate;

public class QuoteResponse {

    private Long id;
    private String quoteNumber;
    private String insuredName;

    private String vehicleRegNo;
    private String vehicleMake;
    private String vehicleModel;
    private int vehicleYear;

    private LocalDate quoteDate;
    private double premiumEstimate;
    private String status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getQuoteNumber() { return quoteNumber; }
    public void setQuoteNumber(String quoteNumber) { this.quoteNumber = quoteNumber; }

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

    public LocalDate getQuoteDate() { return quoteDate; }
    public void setQuoteDate(LocalDate quoteDate) { this.quoteDate = quoteDate; }

    public double getPremiumEstimate() { return premiumEstimate; }
    public void setPremiumEstimate(double premiumEstimate) { this.premiumEstimate = premiumEstimate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
