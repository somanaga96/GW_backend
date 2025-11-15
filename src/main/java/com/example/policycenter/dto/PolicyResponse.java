package com.example.policycenter.dto;

import com.example.policycenter.model.PolicyStatus;

import java.time.LocalDate;

public class PolicyResponse {

    private Long id;
    private String policyNumber;
    private String insuredName;

    private String vehicleRegNo;
    private String vehicleMake;
    private String vehicleModel;
    private int vehicleYear;

    private LocalDate effectiveDate;
    private LocalDate expiryDate;

    private double premium;

    private PolicyStatus status;

    private Long quoteId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPolicyNumber() { return policyNumber; }
    public void setPolicyNumber(String policyNumber) { this.policyNumber = policyNumber; }

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

    public LocalDate getEffectiveDate() { return effectiveDate; }
    public void setEffectiveDate(LocalDate effectiveDate) { this.effectiveDate = effectiveDate; }

    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }

    public double getPremium() { return premium; }
    public void setPremium(double premium) { this.premium = premium; }

    public PolicyStatus getStatus() { return status; }
    public void setStatus(PolicyStatus status) { this.status = status; }

    public Long getQuoteId() { return quoteId; }
    public void setQuoteId(Long quoteId) { this.quoteId = quoteId; }
}
