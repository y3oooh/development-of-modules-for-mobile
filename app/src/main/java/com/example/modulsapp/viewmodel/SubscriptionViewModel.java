package com.example.modulsapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.modulsapp.data.SubscriptionOptions;
import com.example.modulsapp.data.Tariff;
import com.example.modulsapp.data.StorageOption;

public class SubscriptionViewModel extends ViewModel {

    private final MutableLiveData<SubscriptionState> state =
            new MutableLiveData<>(SubscriptionState.initial());

    public LiveData<SubscriptionState> getState() {
        return state;
    }

    public void selectTariff(int position) {
        SubscriptionState s = state.getValue();
        if (s != null && position >= 0 && position < SubscriptionOptions.TARIFFS.size()) {
            state.setValue(s.withTariff(SubscriptionOptions.TARIFFS.get(position)));
        }
    }

    public void incrementUserCount() {
        SubscriptionState s = state.getValue();
        if (s != null) setUserCount(s.getUserCount() + 1);
    }

    public void decrementUserCount() {
        SubscriptionState s = state.getValue();
        if (s != null) setUserCount(s.getUserCount() - 1);
    }

    public void setUserCount(int count) {
        SubscriptionState s = state.getValue();
        if (s != null) {
            int clamped = Math.max(1, Math.min(100, count));
            state.setValue(s.withUserCount(clamped));
        }
    }

    public void selectStorage(int position) {
        SubscriptionState s = state.getValue();
        if (s != null && position >= 0 && position < SubscriptionOptions.STORAGE_OPTIONS.size()) {
            state.setValue(s.withStorage(SubscriptionOptions.STORAGE_OPTIONS.get(position)));
        }
    }

    public void toggleYearly() {
        SubscriptionState s = state.getValue();
        if (s != null) {
            state.setValue(s.withYearly(!s.isYearly()));
        }
    }

    public int calculateTotal(SubscriptionState s) {
        if (s.getSelectedTariff() == null) return 0;
        int total = s.getSelectedTariff().getBasePrice();
        total += s.getStorage().getPrice();
        total *= s.getUserCount();
        if (s.isYearly()) {
            total = total * (100 - SubscriptionOptions.YEARLY_DISCOUNT_PERCENT) / 100;
            total *= 12;
        }
        return total;
    }

    public void setBudget(int budget) {
        SubscriptionState s = state.getValue();
        if (s != null) {
            state.setValue(s.withBudget(budget));
        }
    }
}