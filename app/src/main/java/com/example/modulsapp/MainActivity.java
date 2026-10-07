package com.example.modulsapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.modulsapp.data.SubscriptionOptions;
import com.example.modulsapp.view.TariffFillView;
import com.example.modulsapp.viewmodel.SubscriptionState;
import com.example.modulsapp.viewmodel.SubscriptionViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class MainActivity extends AppCompatActivity {

    private static final int BUDGET_REFERENCE_PRICE = 5000;

    private SubscriptionViewModel viewModel;
    private TextView textUserCount, textTotal;
    private MaterialButtonToggleGroup toggleTariff;
    private ChipGroup chipStorage;
    private MaterialButton btnToggleYearly, btnOrder;
    private TariffFillView fillView;
    private TextInputEditText inputBudget;
    private TextInputLayout inputBudgetLayout;

    private final ActivityResultLauncher<Intent> confirmLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            long orderId = result.getData().getLongExtra("orderId", -1);
                            Snackbar.make(findViewById(R.id.main),
                                            "Заказ оформлен. Номер: " + orderId,
                                            Snackbar.LENGTH_LONG)
                                    .setAction("Отменить", v ->
                                            Snackbar.make(findViewById(R.id.main),
                                                            "Заказ №" + orderId + " отменён",
                                                            Snackbar.LENGTH_SHORT)
                                                    .show())
                                    .show();
                        }
                    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textUserCount = findViewById(R.id.text_user_count);
        textTotal = findViewById(R.id.text_total);
        toggleTariff = findViewById(R.id.toggle_tariff);
        chipStorage = findViewById(R.id.chip_storage);
        btnToggleYearly = findViewById(R.id.btn_toggle_yearly);
        btnOrder = findViewById(R.id.btn_order);
        fillView = findViewById(R.id.tariff_fill_view);
        inputBudget = findViewById(R.id.input_budget);
        inputBudgetLayout = findViewById(R.id.input_budget_layout);

        viewModel = new ViewModelProvider(this).get(SubscriptionViewModel.class);

        viewModel.getState().observe(this, this::updateUI);

        toggleTariff.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                for (int i = 0; i < group.getChildCount(); i++) {
                    if (group.getChildAt(i).getId() == checkedId) {
                        viewModel.selectTariff(i);
                        break;
                    }
                }
            }
        });

        findViewById(R.id.btn_increment).setOnClickListener(v -> viewModel.incrementUserCount());
        findViewById(R.id.btn_decrement).setOnClickListener(v -> viewModel.decrementUserCount());

        chipStorage.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (!checkedIds.isEmpty()) {
                int id = checkedIds.get(0);
                for (int i = 0; i < group.getChildCount(); i++) {
                    if (group.getChildAt(i).getId() == id) {
                        viewModel.selectStorage(i);
                        break;
                    }
                }
            }
        });

        btnToggleYearly.setOnClickListener(v -> viewModel.toggleYearly());

        btnOrder.setOnClickListener(v -> {
            SubscriptionState state = viewModel.getState().getValue();
            if (state != null) {
                int total = viewModel.calculateTotal(state);
                Intent intent = new Intent(this, ConfirmActivity.class);
                intent.putExtra("total", total);
                confirmLauncher.launch(intent);
            }
        });

        inputBudget.addTextChangedListener(new android.text.TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int a, int b, int c) {}
            @Override public void onTextChanged(CharSequence s, int a, int b, int c) {}
            @Override public void afterTextChanged(android.text.Editable s) {
                String text = s.toString().trim();
                if (text.isEmpty()) return;
                try {
                    int value = Integer.parseInt(text);
                    viewModel.setBudget(value);
                    inputBudgetLayout.setError(null);
                } catch (NumberFormatException e) {
                    inputBudgetLayout.setError("Введите число");
                }
            }
        });
    }

    private void updateUI(SubscriptionState state) {
        textUserCount.setText(String.valueOf(state.getUserCount()));
        int total = viewModel.calculateTotal(state);
        textTotal.setText("Итог: " + total + " ₽");

        int tariffIndex = SubscriptionOptions.TARIFFS.indexOf(state.getSelectedTariff());
        if (tariffIndex >= 0 && tariffIndex < toggleTariff.getChildCount()) {
            toggleTariff.check(toggleTariff.getChildAt(tariffIndex).getId());
        }

        int storageIndex = SubscriptionOptions.STORAGE_OPTIONS.indexOf(state.getStorage());
        if (storageIndex >= 0 && storageIndex < chipStorage.getChildCount()) {
            chipStorage.check(chipStorage.getChildAt(storageIndex).getId());
        }

        btnToggleYearly.setChecked(state.isYearly());

        if (inputBudget != null && !inputBudget.getText().toString()
                .equals(String.valueOf(state.getBudget()))) {
            inputBudget.setText(String.valueOf(state.getBudget()));
            inputBudget.setSelection(inputBudget.getText().length());
        }

        if (fillView != null) {
            float percent = Math.min(100f, (total * 100f) / state.getBudget());
            float saving  = state.isYearly() ? SubscriptionOptions.YEARLY_DISCOUNT_PERCENT : 0f;
            fillView.setData(percent, saving);
        }
    }
}