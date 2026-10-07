package com.example.hotelbooking.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import com.example.hotelbooking.R;
import com.example.hotelbooking.calendar.CalendarHelper;
import com.example.hotelbooking.model.BookingDraft;
import com.example.hotelbooking.permissions.PermissionHelper;
import com.example.hotelbooking.storage.DraftStorage;
import com.example.hotelbooking.validation.FormValidator;
import com.example.hotelbooking.validation.PromoValidator;
import com.example.hotelbooking.validation.ValidationResult;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements ConfirmDialogFragment.Listener {

    private TextInputLayout nameLayout, passportLayout, phoneLayout, emailLayout,
            checkInLayout, checkOutLayout, guestsLayout, roomTypeLayout,
            wishesLayout, promoLayout;
    private TextInputEditText nameEdit, passportEdit, phoneEdit, emailEdit,
            checkInEdit, checkOutEdit, guestsEdit, wishesEdit, promoEdit;
    private MaterialAutoCompleteTextView roomTypeEdit;

    private LinearProgressIndicator progressBar;
    private TextView hintText;
    private MaterialButton submitButton;
    private NestedScrollView scrollView;
    private ViewGroup rootLayout;

    private DraftStorage storage;
    private PermissionHelper permissionHelper;

    private Date checkInDate = null;
    private Date checkOutDate = null;

    private final SimpleDateFormat dateFormat =
            new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

    private boolean blockAfterFailedAttempt = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bindViews();
        setupRoomTypes();
        setupDatePickers();
        setupValidationWatchers();
        setupSubmitButton();

        storage = new DraftStorage(this);
        permissionHelper = new PermissionHelper(this, new PermissionHelper.Callback() {
            @Override
            public void onGranted() {
            }

            @Override
            public void onDenied() {
                Snackbar.make(rootLayout,
                        R.string.msg_permission_denied,
                        Snackbar.LENGTH_LONG).show();
            }
        });

        restoreDraft();
        refreshFormState();
    }

    private void bindViews() {
        nameLayout = findViewById(R.id.nameLayout);
        passportLayout = findViewById(R.id.passportLayout);
        phoneLayout = findViewById(R.id.phoneLayout);
        emailLayout = findViewById(R.id.emailLayout);
        checkInLayout = findViewById(R.id.checkInLayout);
        checkOutLayout = findViewById(R.id.checkOutLayout);
        guestsLayout = findViewById(R.id.guestsLayout);
        roomTypeLayout = findViewById(R.id.roomTypeLayout);
        wishesLayout = findViewById(R.id.wishesLayout);
        promoLayout = findViewById(R.id.promoLayout);

        nameEdit = findViewById(R.id.nameEdit);
        passportEdit = findViewById(R.id.passportEdit);
        phoneEdit = findViewById(R.id.phoneEdit);
        emailEdit = findViewById(R.id.emailEdit);
        checkInEdit = findViewById(R.id.checkInEdit);
        checkOutEdit = findViewById(R.id.checkOutEdit);
        guestsEdit = findViewById(R.id.guestsEdit);
        roomTypeEdit = findViewById(R.id.roomTypeEdit);
        wishesEdit = findViewById(R.id.wishesEdit);
        promoEdit = findViewById(R.id.promoEdit);

        progressBar = findViewById(R.id.progressBar);
        hintText = findViewById(R.id.hintText);
        submitButton = findViewById(R.id.submitButton);
        scrollView = findViewById(R.id.scrollView);
        rootLayout = findViewById(R.id.rootLayout);
    }

    private void setupRoomTypes() {
        String[] types = getResources().getStringArray(R.array.room_types);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, types);
        roomTypeEdit.setAdapter(adapter);
    }

    private void setupDatePickers() {
        checkInEdit.setOnClickListener(v -> openDatePicker(true));
        checkInLayout.setEndIconOnClickListener(v -> openDatePicker(true));
        checkOutEdit.setOnClickListener(v -> openDatePicker(false));
        checkOutLayout.setEndIconOnClickListener(v -> openDatePicker(false));
    }

    private void openDatePicker(boolean isCheckIn) {
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText(isCheckIn ? "Выберите дату заезда" : "Выберите дату выезда");

        if (isCheckIn) {
            builder.setSelection(MaterialDatePicker.todayInUtcMilliseconds());
        } else if (checkInDate != null) {
            builder.setSelection(checkInDate.getTime());
        }

        MaterialDatePicker<Long> picker = builder.build();
        picker.addOnPositiveButtonClickListener(selection -> {
            Date chosen = new Date(selection);
            if (isCheckIn) {
                checkInDate = chosen;
                checkInEdit.setText(dateFormat.format(chosen));
                if (checkOutDate != null && !checkOutDate.after(checkInDate)) {
                    checkOutDate = null;
                    checkOutEdit.setText("");
                }
            } else {
                checkOutDate = chosen;
                checkOutEdit.setText(dateFormat.format(chosen));
            }
            onFieldChanged();
            checkInLayout.setError(null);
            checkOutLayout.setError(null);
        });
        picker.show(getSupportFragmentManager(), "date_picker");
    }

    private void setupValidationWatchers() {
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                onFieldChanged();
            }
        };

        nameEdit.addTextChangedListener(watcher);
        passportEdit.addTextChangedListener(watcher);
        phoneEdit.addTextChangedListener(watcher);
        emailEdit.addTextChangedListener(watcher);
        guestsEdit.addTextChangedListener(watcher);
        wishesEdit.addTextChangedListener(watcher);
        promoEdit.addTextChangedListener(watcher);
        roomTypeEdit.addTextChangedListener(watcher);

        setupFocusClear(nameEdit, nameLayout);
        setupFocusClear(passportEdit, passportLayout);
        setupFocusClear(phoneEdit, phoneLayout);
        setupFocusClear(emailEdit, emailLayout);
        setupFocusClear(guestsEdit, guestsLayout);
        setupFocusClear(promoEdit, promoLayout);
    }

    private void setupFocusClear(TextInputEditText edit, TextInputLayout layout) {
        edit.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                ValidationResult r = FormValidator.validate(collectDraft());
                animateError(layout, r.errors.get(layout.getId()));
            }
        });
    }

    private void onFieldChanged() {
        saveDraft();
        refreshFormState();

        String promo = text(promoEdit);
        if (!promo.isEmpty() && !PromoValidator.isValid(promo)) {
            if (!promoEdit.hasFocus()) {
                promoLayout.setError("Промокод не найден");
            }
        } else {
            promoLayout.setError(null);
        }
    }

    private void refreshFormState() {
        ValidationResult r = FormValidator.validate(collectDraft());
        boolean allFilled = areAllRequiredFieldsFilled();

        if (blockAfterFailedAttempt) {
            submitButton.setEnabled(r.valid);
            if (r.valid) {
                blockAfterFailedAttempt = false;
            }
        } else {
            submitButton.setEnabled(allFilled);
        }

        int percent = calculateProgress();
        progressBar.setProgressCompat(percent, true);

        hintText.setText(r.valid ? R.string.hint_form_ready : R.string.hint_fill_form);
    }

    private boolean areAllRequiredFieldsFilled() {
        if (nameEdit.getText() == null || nameEdit.getText().toString().trim().isEmpty()) return false;
        if (passportEdit.getText() == null || passportEdit.getText().toString().trim().isEmpty()) return false;
        if (phoneEdit.getText() == null || phoneEdit.getText().toString().trim().isEmpty()) return false;
        if (emailEdit.getText() == null || emailEdit.getText().toString().trim().isEmpty()) return false;
        if (checkInDate == null) return false;
        if (checkOutDate == null) return false;
        if (guestsEdit.getText() == null || guestsEdit.getText().toString().trim().isEmpty()) return false;
        if (roomTypeEdit.getText() == null || roomTypeEdit.getText().toString().trim().isEmpty()) return false;
        return true;
    }

    private int calculateProgress() {
        int total = 8;
        int filled = 0;

        if (!nameEdit.getText().toString().trim().isEmpty()) filled++;
        if (!passportEdit.getText().toString().trim().isEmpty()) filled++;
        if (!phoneEdit.getText().toString().trim().isEmpty()) filled++;
        if (!emailEdit.getText().toString().trim().isEmpty()) filled++;
        if (checkInDate != null) filled++;
        if (checkOutDate != null) filled++;
        if (!guestsEdit.getText().toString().trim().isEmpty()) filled++;
        if (!roomTypeEdit.getText().toString().trim().isEmpty()) filled++;

        return (int) ((filled / (float) total) * 100);
    }

    private void animateError(TextInputLayout layout, String error) {
        TransitionManager.beginDelayedTransition(rootLayout);
        layout.setError(error);
    }

    private void applyAllErrors(ValidationResult r) {
        TransitionManager.beginDelayedTransition(rootLayout);
        nameLayout.setError(r.errors.get(R.id.nameLayout));
        passportLayout.setError(r.errors.get(R.id.passportLayout));
        phoneLayout.setError(r.errors.get(R.id.phoneLayout));
        emailLayout.setError(r.errors.get(R.id.emailLayout));
        checkInLayout.setError(r.errors.get(R.id.checkInLayout));
        checkOutLayout.setError(r.errors.get(R.id.checkOutLayout));
        guestsLayout.setError(r.errors.get(R.id.guestsLayout));
        roomTypeLayout.setError(r.errors.get(R.id.roomTypeLayout));
        promoLayout.setError(r.errors.get(R.id.promoLayout));
    }

    private void setupSubmitButton() {
        submitButton.setOnClickListener(v -> {
            ValidationResult r = FormValidator.validate(collectDraft());
            applyAllErrors(r);

            if (!r.valid) {
                blockAfterFailedAttempt = true;
                refreshFormState();
                scrollView.postDelayed(() -> scrollToFirstError(r), 400);
                Snackbar.make(rootLayout,
                        "Проверьте выделенные поля",
                        Snackbar.LENGTH_SHORT).show();
                return;
            }

            BookingDraft draft = collectDraft();
            ConfirmDialogFragment dialog = ConfirmDialogFragment.newInstance(draft);
            dialog.show(getSupportFragmentManager(), "confirm");
        });
    }

    private void scrollToFirstError(ValidationResult r) {
        if (r.errors.isEmpty()) return;

        TextInputLayout target = null;
        int minY = Integer.MAX_VALUE;

        int[] scrollLoc = new int[2];
        scrollView.getLocationOnScreen(scrollLoc);

        for (Integer id : r.errors.keySet()) {
            View v = findViewById(id);
            if (!(v instanceof TextInputLayout)) continue;

            int[] loc = new int[2];
            v.getLocationOnScreen(loc);

            int y = loc[1] - scrollLoc[1] + scrollView.getScrollY();
            if (y < minY) {
                minY = y;
                target = (TextInputLayout) v;
            }
        }

        if (target == null) return;

        final TextInputLayout targetLayout = target;
        final int finalMinY = minY;

        if (targetLayout.getEditText() != null) {
            targetLayout.getEditText().requestFocus();
        }

        scrollView.post(() -> {
            int appBarHeight = 0;
            View appBar = findViewById(R.id.appBar);
            if (appBar != null) appBarHeight = appBar.getHeight();

            int targetY = Math.max(0, finalMinY - appBarHeight - 24);
            scrollView.smoothScrollTo(0, targetY);
        });
    }

    @Override
    public void onConfirmed() {
        BookingDraft draft = collectDraft();

        boolean added = CalendarHelper.addToCalendar(this, draft);
        if (!added) {
            Snackbar.make(rootLayout,
                    R.string.msg_no_calendar,
                    Snackbar.LENGTH_LONG).show();
        }

        permissionHelper.requestNotifications(
                this,
                getString(R.string.dialog_permission_title),
                getString(R.string.dialog_permission_message),
                null
        );

        storage.clear();
        Snackbar.make(rootLayout,
                R.string.msg_booking_success,
                Snackbar.LENGTH_LONG).show();

        clearForm();
    }

    private BookingDraft collectDraft() {
        BookingDraft d = new BookingDraft();
        d.name = text(nameEdit);
        d.passport = text(passportEdit);
        d.phone = text(phoneEdit);
        d.email = text(emailEdit);
        d.checkIn = checkInDate;
        d.checkOut = checkOutDate;
        d.roomType = roomTypeEdit.getText() != null
                ? roomTypeEdit.getText().toString().trim() : "";
        d.wishes = text(wishesEdit);
        d.promo = text(promoEdit);

        String guestsStr = text(guestsEdit);
        if (!guestsStr.isEmpty()) {
            try {
                d.guests = Integer.parseInt(guestsStr);
            } catch (NumberFormatException e) {
                d.guests = 0;
            }
        }
        return d;
    }

    private String text(TextInputEditText edit) {
        return edit.getText() != null ? edit.getText().toString().trim() : "";
    }

    private void saveDraft() {
        storage.save(collectDraft());
    }

    private void restoreDraft() {
        BookingDraft d = storage.load();
        if (d.isEmpty()) return;

        nameEdit.setText(d.name);
        passportEdit.setText(d.passport);
        phoneEdit.setText(d.phone);
        emailEdit.setText(d.email);
        guestsEdit.setText(d.guests > 0 ? String.valueOf(d.guests) : "");
        roomTypeEdit.setText(d.roomType, false);
        wishesEdit.setText(d.wishes);
        promoEdit.setText(d.promo);

        if (d.checkIn != null) {
            checkInDate = d.checkIn;
            checkInEdit.setText(dateFormat.format(d.checkIn));
        }
        if (d.checkOut != null) {
            checkOutDate = d.checkOut;
            checkOutEdit.setText(dateFormat.format(d.checkOut));
        }

        Snackbar.make(rootLayout,
                "Черновик восстановлен",
                Snackbar.LENGTH_SHORT).show();
    }

    private void clearForm() {
        nameEdit.setText("");
        passportEdit.setText("");
        phoneEdit.setText("");
        emailEdit.setText("");
        guestsEdit.setText("");
        roomTypeEdit.setText("", false);
        wishesEdit.setText("");
        promoEdit.setText("");
        checkInEdit.setText("");
        checkOutEdit.setText("");
        checkInDate = null;
        checkOutDate = null;

        nameLayout.setError(null);
        passportLayout.setError(null);
        phoneLayout.setError(null);
        emailLayout.setError(null);
        checkInLayout.setError(null);
        checkOutLayout.setError(null);
        guestsLayout.setError(null);
        roomTypeLayout.setError(null);
        promoLayout.setError(null);

        blockAfterFailedAttempt = false;
        refreshFormState();
    }
}