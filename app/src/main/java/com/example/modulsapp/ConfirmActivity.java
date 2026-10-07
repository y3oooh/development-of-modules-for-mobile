package com.example.modulsapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class ConfirmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        TextView textTotal = findViewById(R.id.text_confirm_total);
        MaterialButton btnConfirm = findViewById(R.id.btn_confirm);
        MaterialButton btnCancel = findViewById(R.id.btn_cancel);

        int total = getIntent().getIntExtra("total", 0);
        textTotal.setText("Итог: " + total + " ₽");

        btnConfirm.setOnClickListener(v -> {
            Intent result = new Intent();
            result.putExtra("orderId", System.currentTimeMillis());
            setResult(RESULT_OK, result);
            finish();
        });

        btnCancel.setOnClickListener(v -> finish());
    }
}