package com.example.hotelbooking.calendar;

import android.app.Activity;
import android.content.Intent;
import android.provider.CalendarContract;

import com.example.hotelbooking.model.BookingDraft;

import java.util.Calendar;

public class CalendarHelper {

    public static boolean addToCalendar(Activity activity, BookingDraft d) {
        Calendar begin = Calendar.getInstance();
        begin.setTime(d.checkIn);
        begin.set(Calendar.HOUR_OF_DAY, 14);
        begin.set(Calendar.MINUTE, 0);
        long beginMs = begin.getTimeInMillis();

        Calendar end = Calendar.getInstance();
        end.setTime(d.checkOut);
        end.set(Calendar.HOUR_OF_DAY, 12);
        end.set(Calendar.MINUTE, 0);
        long endMs = end.getTimeInMillis();

        String title = "Отель, " + d.roomType + ", " + d.guests + " гост.";
        String description = "Гость: " + d.name
                + "\nТелефон: " + d.phone
                + "\nEmail: " + d.email
                + (d.wishes.isEmpty() ? "" : "\nПожелания: " + d.wishes);

        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.TITLE, title)
                .putExtra(CalendarContract.Events.DESCRIPTION, description)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginMs)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endMs)
                .putExtra(CalendarContract.Events.HAS_ALARM, 1);

        if (intent.resolveActivity(activity.getPackageManager()) == null) {
            return false;
        }

        activity.startActivity(intent);
        return true;
    }
}