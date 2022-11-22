package dev.ecommerce.shared.helpers;

import java.util.Objects;

public class Data {
    public static <T> T getValueOrDefault(T value, T defaultValue) {
        return Objects.requireNonNullElse(value, defaultValue);
    }
}
