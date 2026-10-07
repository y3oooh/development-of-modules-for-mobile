package com.example.hotelbooking.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.hotelbooking.R;
import com.example.hotelbooking.model.BookingDraft;
import com.example.hotelbooking.validation.PromoValidator;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ConfirmDialogFragment extends DialogFragment {

    public interface Listener {
        void onConfirmed();
    }

    private static final String ARG_NAME = "name";
    private static final String ARG_PASSPORT = "passport";
    private static final String ARG_PHONE = "phone";
    private static final String ARG_EMAIL = "email";
    private static final String ARG_CHECK_IN = "check_in";
    private static final String ARG_CHECK_OUT = "check_out";
    private static final String ARG_GUESTS = "guests";
    private static final String ARG_ROOM_TYPE = "room_type";
    private static final String ARG_WISHES = "wishes";
    private static final String ARG_PROMO = "promo";

    public static ConfirmDialogFragment newInstance(BookingDraft d) {
        ConfirmDialogFragment f = new ConfirmDialogFragment();
        Bundle b = new Bundle();
        b.putString(ARG_NAME, d.name);
        b.putString(ARG_PASSPORT, d.passport);
        b.putString(ARG_PHONE, d.phone);
        b.putString(ARG_EMAIL, d.email);
        b.putLong(ARG_CHECK_IN, d.checkIn != null ? d.checkIn.getTime() : -1L);
        b.putLong(ARG_CHECK_OUT, d.checkOut != null ? d.checkOut.getTime() : -1L);
        b.putInt(ARG_GUESTS, d.guests);
        b.putString(ARG_ROOM_TYPE, d.roomType);
        b.putString(ARG_WISHES, d.wishes);
        b.putString(ARG_PROMO, d.promo);
        f.setArguments(b);
        return f;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(requireContext())
                .inflate(R.layout.dialog_confirm, null, false);

        Bundle a = requireArguments();
        SimpleDateFormat fmt = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

        String name = a.getString(ARG_NAME, "");
        String passport = a.getString(ARG_PASSPORT, "");
        String phone = a.getString(ARG_PHONE, "");
        String email = a.getString(ARG_EMAIL, "");
        long ci = a.getLong(ARG_CHECK_IN, -1L);
        long co = a.getLong(ARG_CHECK_OUT, -1L);
        int guests = a.getInt(ARG_GUESTS, 0);
        String roomType = a.getString(ARG_ROOM_TYPE, "");
        String wishes = a.getString(ARG_WISHES, "");
        String promo = a.getString(ARG_PROMO, "");

        ((TextView) view.findViewById(R.id.confirmName)).setText("Гость: " + name);
        ((TextView) view.findViewById(R.id.confirmPassport)).setText("Паспорт: " + passport);
        ((TextView) view.findViewById(R.id.confirmPhone)).setText("Телефон: " + phone);
        ((TextView) view.findViewById(R.id.confirmEmail)).setText("Email: " + email);
        ((TextView) view.findViewById(R.id.confirmDates)).setText(
                "Заезд: " + (ci != -1L ? fmt.format(ci) : "—")
                        + "\nВыезд: " + (co != -1L ? fmt.format(co) : "—"));
        ((TextView) view.findViewById(R.id.confirmGuests)).setText("Гостей: " + guests);
        ((TextView) view.findViewById(R.id.confirmRoomType)).setText("Номер: " + roomType);

        TextView promoView = view.findViewById(R.id.confirmPromo);
        int discount = PromoValidator.discountPercent(promo);
        if (discount > 0) {
            promoView.setText("Промокод " + promo.toUpperCase() + ": скидка " + discount + "%");
            promoView.setVisibility(View.VISIBLE);
        } else if (!promo.isEmpty()) {
            promoView.setText("Промокод " + promo.toUpperCase() + ": не найден");
            promoView.setVisibility(View.VISIBLE);
        } else {
            promoView.setVisibility(View.GONE);
        }

        TextView wishesView = view.findViewById(R.id.confirmWishes);
        if (wishes.isEmpty()) {
            wishesView.setVisibility(View.GONE);
        } else {
            wishesView.setVisibility(View.VISIBLE);
            wishesView.setText("Пожелания: " + wishes);
        }

        return new MaterialAlertDialogBuilder(requireContext())
                .setView(view)
                .setPositiveButton(R.string.btn_confirm, (dialog, which) -> {
                    if (getActivity() instanceof Listener) {
                        ((Listener) getActivity()).onConfirmed();
                    }
                })
                .setNegativeButton(R.string.btn_cancel, null)
                .create();
    }
}