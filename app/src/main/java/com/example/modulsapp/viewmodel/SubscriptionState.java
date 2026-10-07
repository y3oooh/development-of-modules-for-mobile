package com.example.modulsapp.viewmodel;

import com.example.modulsapp.data.Tariff;
import com.example.modulsapp.data.StorageOption;
import com.example.modulsapp.data.SubscriptionOptions;

public class SubscriptionState {
    private final Tariff selectedTariff;
    private final int userCount;
    private final StorageOption storage;
    private final boolean isYearly;
    private final int budget;

    public SubscriptionState(Tariff selectedTariff, int userCount,
                             StorageOption storage, boolean isYearly, int budget) {
        this.selectedTariff = selectedTariff;
        this.userCount = Math.max(1, userCount);
        this.storage = storage;
        this.isYearly = isYearly;
        this.budget = Math.max(100, budget);
    }

    public Tariff getSelectedTariff() { return selectedTariff; }
    public int getUserCount() { return userCount; }
    public StorageOption getStorage() { return storage; }
    public boolean isYearly() { return isYearly; }
    public int getBudget() { return budget; }

    public SubscriptionState withTariff(Tariff tariff) {
        return new SubscriptionState(tariff, userCount, storage, isYearly, budget);
    }
    public SubscriptionState withUserCount(int count) {
        return new SubscriptionState(selectedTariff, count, storage, isYearly, budget);
    }
    public SubscriptionState withStorage(StorageOption storage) {
        return new SubscriptionState(selectedTariff, userCount, storage, isYearly, budget);
    }
    public SubscriptionState withYearly(boolean yearly) {
        return new SubscriptionState(selectedTariff, userCount, storage, yearly, budget);
    }
    public SubscriptionState withBudget(int budget) {
        return new SubscriptionState(selectedTariff, userCount, storage, isYearly, budget);
    }

    public static SubscriptionState initial() {
        return new SubscriptionState(
                SubscriptionOptions.TARIFFS.get(0),
                1,
                SubscriptionOptions.STORAGE_OPTIONS.get(0),
                false,
                5000
        );
    }
}