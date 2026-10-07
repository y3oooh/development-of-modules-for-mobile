package com.example.hotelbooking.validation;

import android.util.Patterns;

import com.example.hotelbooking.R;
import com.example.hotelbooking.model.BookingDraft;

public class FormValidator {

    public static ValidationResult validate(BookingDraft d) {
        ValidationResult r = new ValidationResult();

        String name = d.name.trim();
        if (name.isEmpty() || name.split("\\s+").length < 2) {
            r.addError(R.id.nameLayout, "Введите фамилию и имя");
        }

        String passport = d.passport.replaceAll("\\s", "");
        if (!passport.matches("\\d{10}")) {
            r.addError(R.id.passportLayout, "Серия 4 цифры, номер 6 цифр");
        }

        String phone = d.phone.trim();
        if (phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches()) {
            r.addError(R.id.phoneLayout, "Формат: +7 900 000-00-00");
        }

        String email = d.email.trim();
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            r.addError(R.id.emailLayout, "Некорректный email");
        }

        if (d.checkIn == null) {
            r.addError(R.id.checkInLayout, "Выберите дату заезда");
        }

        if (d.checkOut == null) {
            r.addError(R.id.checkOutLayout, "Выберите дату выезда");
        } else if (d.checkIn != null && !d.checkOut.after(d.checkIn)) {
            r.addError(R.id.checkOutLayout, "Выезд должен быть позже заезда");
        }

        if (d.guests < 1 || d.guests > 10) {
            r.addError(R.id.guestsLayout, "От 1 до 10 гостей");
        }

        if (d.roomType == null || d.roomType.trim().isEmpty()) {
            r.addError(R.id.roomTypeLayout, "Выберите категорию");
        }

        String promo = d.promo.trim();
        if (!promo.isEmpty() && !PromoValidator.isValid(promo)) {
            r.addError(R.id.promoLayout, "Промокод не найден");
        }

        return r;
    }
}