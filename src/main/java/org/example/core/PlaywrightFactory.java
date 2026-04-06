package org.example.core;

import com.microsoft.playwright.Playwright;

public class PlaywrightFactory {

    private static Playwright playwright;

    public static Playwright get() {
        if (playwright == null) {
            playwright = Playwright.create();
            System.out.println("🚀 Playwright инициализирован");
        }
        return playwright;
    }
}