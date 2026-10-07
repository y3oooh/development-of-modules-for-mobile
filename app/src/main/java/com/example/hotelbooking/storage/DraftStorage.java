package com.example.hotelbooking.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.hotelbooking.model.BookingDraft;

import java.util.Date;

public class DraftStorage {

    private static final String PREFS_NAME = "hotel_booking_draft";

    private static final String KEY_NAME = "name";
    private static final String KEY_PASSPORT = "passport";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_CHECK_IN = "check_in";
    private static final String KEY_CHECK_OUT = "check_out";
    private static final String KEY_GUESTS = "guests";
    private static final String KEY_ROOM_TYPE = "room_type";
    private static final String KEY_PROMO = "promo";
    private static final String KEY_WISHES = "wishes";

    private final SharedPreferences prefs;

    public DraftStorage(Context context) {
        prefs = context.getApplicationContext()
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void save(BookingDraft d) {
        prefs.edit()
                .putString(KEY_NAME, d.name)
                .putString(KEY_PASSPORT, d.passport)
                .putString(KEY_PHONE, d.phone)
                .putString(KEY_EMAIL, d.email)
                .putLong(KEY_CHECK_IN, d.checkIn != null ? d.checkIn.getTime() : -1L)
                .putLong(KEY_CHECK_OUT, d.checkOut != null ? d.checkOut.getTime() : -1L)
                .putInt(KEY_GUESTS, d.guests)
                .putString(KEY_ROOM_TYPE, d.roomType)
                .putString(KEY_PROMO, d.promo)
                .putString(KEY_WISHES, d.wishes)
                .apply();
    }

    public BookingDraft load() {
        BookingDraft d = new BookingDraft();
        d.name = prefs.getString(KEY_NAME, "");
        d.passport = prefs.getString(KEY_PASSPORT, "");
        d.phone = prefs.getString(KEY_PHONE, "");
        d.email = prefs.getString(KEY_EMAIL, "");

        long ci = prefs.getLong(KEY_CHECK_IN, -1L);
        if (ci != -1L) d.checkIn = new Date(ci);

        long co = prefs.getLong(KEY_CHECK_OUT, -1L);
        if (co != -1L) d.checkOut = new Date(co);

        d.guests = prefs.getInt(KEY_GUESTS, 0);
        d.roomType = prefs.getString(KEY_ROOM_TYPE, "");
        d.promo = prefs.getString(KEY_PROMO, "");
        d.wishes = prefs.getString(KEY_WISHES, "");
        return d;
    }

    public void clear() {
        prefs.edit().clear().apply();
    }
}