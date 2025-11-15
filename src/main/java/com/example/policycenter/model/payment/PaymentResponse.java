package com.example.policycenter.model.payment;
public class PaymentResponse {

    private String status;
    private String message;
    private Long policyId;

    public PaymentResponse(String status, String message, Long policyId) {
        this.status = status;
        this.message = message;
        this.policyId = policyId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }
}
