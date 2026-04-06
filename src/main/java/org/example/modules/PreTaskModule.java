package org.example.modules;

import com.microsoft.playwright.*;

public class PreTaskModule {

    private final Page page;

    public PreTaskModule(Page page) {
        this.page = page;
    }

    public void handlePreTaskForm() {

        Locator form = page.locator("#lmodel-form");

        if (form.count() == 0) {
            return; // формы нет → сразу model-form
        }

        System.out.println("📍 Найдена pre-form");

        // 🔥 ждём пока появится input
        Locator input = form.locator("input[name*='url']");
        input.waitFor();

        // ❗ ВАЖНО: сначала клик → потом fill
        input.click();
        input.fill("https://example.com");

        // иногда нужен blur (очень важно!)
        input.press("Tab");

        // 🔥 submit

        Locator submit = form.locator("input[type='submit']");
        submit.click();

        // ❗ ВАЖНО: ждём ЛИБО model-form, ЛИБО ошибку
        page.waitForSelector("#model-form, .errorMessage", new Page.WaitForSelectorOptions().setTimeout(10000));

        System.out.println("✅ pre-form обработана");
    }
}