package com.example.demo.model.room;

import jakarta.persistence.Embeddable;

@Embeddable
public class Occupancy {

    private int maxGuests;
    private int maxAdults;
    private int maxChildrens;

    public Occupancy() {
    }

    public Occupancy(int maxGuests, int maxAdults, int maxChildrens) {
        this.maxGuests = maxGuests;
        this.maxAdults = maxAdults;
        this.maxChildrens = maxChildrens;
    }

    public int getMaxGuests() {
        return maxGuests;
    }

    public void setMaxGuests(int maxGuests) {
        this.maxGuests = maxGuests;
    }

    public int getMaxAdults() {
        return maxAdults;
    }

    public void setMaxAdults(int maxAdults) {
        this.maxAdults = maxAdults;
    }

    public int getMaxChildrens() {
        return maxChildrens;
    }

    public void setMaxChildrens(int maxChildrens) {
        this.maxChildrens = maxChildrens;
    }

}
