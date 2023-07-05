package com.ItsTime.ItNovation.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Grade {
    WELCOME("WELCOME"),
    SILVER("SILVER"),
    GOLD("GOLD"),
    VIP("VIP");

    private final String value;


    public static Grade getDefault() {
        return WELCOME;
    }
    public String getValue() {
        return value;
    }
}
