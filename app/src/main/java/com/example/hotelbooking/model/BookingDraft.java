package com.example.hotelbooking.model;

import java.util.Date;

public class BookingDraft {

    public String name = "";
    public String passport = "";
    public String phone = "";
    public String email = "";
    public Date checkIn = null;
    public Date checkOut = null;
    public int guests = 0;
    public String roomType = "";
    public String wishes = "";
    public String promo = "";

    public boolean isEmpty() {
        return name.isEmpty()
                && passport.isEmpty()
                && phone.isEmpty()
                && email.isEmpty()
                && checkIn == null
                && checkOut == null
                && guests == 0
                && roomType.isEmpty()
                && wishes.isEmpty()
                && promo.isEmpty();
    }
}