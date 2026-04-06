package org.example.core;

import com.microsoft.playwright.*;

public class BrowserFactory {

    public static Browser create(Playwright playwright) {
        return playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
        );
    }
}
