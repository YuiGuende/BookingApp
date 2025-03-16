package com.example.demo.dto.order;

public class PaymentDTO {

    private long amount;
    private String bankCode;
    private Long bookingId;
    

    public PaymentDTO() {
    }


    public PaymentDTO(Long amount, String bankCode) {
        this.amount = amount;
        this.bankCode = bankCode;
    }
    

    public PaymentDTO(long amount, String bankCode, Long bookingId) {
        this.amount = amount;
        this.bankCode = bankCode;
        this.bookingId = bookingId;
    }


    public void setAmount(long amount) {
        this.amount = amount;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }


    @Override
    public String toString() {
        return "PaymentDTO [amount=" + amount + ", bankCode=" + bankCode + ", bookingId=" + bookingId + "]";
    }

    

}
