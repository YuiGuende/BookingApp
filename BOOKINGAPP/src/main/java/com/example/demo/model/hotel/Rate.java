package com.example.demo.model.hotel;

import jakarta.persistence.Embeddable;

@Embeddable
public class Rate {

    private double rate;
    private int rateQuantity;

    public Rate() {
        this.rate=0;
        this.rateQuantity=0;
    }

    public Rate(double rate, int rateQuantity) {
        this.rate = rate;
        this.rateQuantity = rateQuantity;
    }

    public void addRate(double rate) {
        this.rate = (this.rate * rateQuantity + rate) / (rateQuantity + 1);
        rateQuantity++;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getRateQuantity() {
        return rateQuantity;
    }

    public void setRateQuantity(int rateQuantity) {
        this.rateQuantity = rateQuantity;
    }

}
