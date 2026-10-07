package com.example.hotelbooking.permissions;

import android.Manifest;
import android.app.Activity;
import android.os.Build;

import androidx.activity.ComponentActivity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class PermissionHelper {

    public interface Callback {
        void onGranted();
        void onDenied();
    }

    private final ActivityResultLauncher<String> launcher;

    public PermissionHelper(ComponentActivity activity, Callback cb) {
        launcher = activity.registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                granted -> {
                    if (granted) cb.onGranted();
                    else cb.onDenied();
                });
    }

    public void requestNotifications(Activity activity, String title, String message, Runnable onDenied) {
        if (Build.VERSION.SDK_INT < 33) {
            return;
        }

        new MaterialAlertDialogBuilder(activity)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Разрешить", (dialog, which) ->
                        launcher.launch(Manifest.permission.POST_NOTIFICATIONS))
                .setNegativeButton("Не надо", (dialog, which) -> {
                    if (onDenied != null) onDenied.run();
                })
                .show();
    }
}