package com.valkyrie.fine.model;

public class FineDTO {
    private String id;
    private String memberId;
    private double amount;
    private String reason;
    private boolean paidStatus;

    public String getId() {
        return id;
    }

    public FineDTO setId(String id) {
        this.id = id;
        return this;
    }

    public String getMemberId() {
        return memberId;
    }

    public FineDTO setMemberId(String memberId) {
        this.memberId = memberId;
        return this;
    }

    public double getAmount() {
        return amount;
    }

    public FineDTO setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public FineDTO setReason(String reason) {
        this.reason = reason;
        return this;
    }

    public boolean isPaidStatus() {
        return paidStatus;
    }

    public FineDTO setPaidStatus(boolean paidStatus) {
        this.paidStatus = paidStatus;
        return this;
    }

}
