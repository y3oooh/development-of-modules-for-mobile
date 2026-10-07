package com.example.modulsapp.data;

import java.util.Arrays;
import java.util.List;

public class SubscriptionOptions {
    public static final List<Tariff> TARIFFS = Arrays.asList(
            new Tariff("Базовый", 299),
            new Tariff("Стандарт", 499),
            new Tariff("Премиум", 799),
            new Tariff("Бизнес", 1299),
            new Tariff("Корпоративный", 2499)
    );

    public static final List<StorageOption> STORAGE_OPTIONS = Arrays.asList(
            new StorageOption(0, 0),
            new StorageOption(50, 199),
            new StorageOption(100, 349),
            new StorageOption(200, 599)
    );

    public static final int YEARLY_DISCOUNT_PERCENT = 20;
}
