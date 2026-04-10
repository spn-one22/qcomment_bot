package org.example.utils;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.Random;

public class FormFiller {

    // ---------------------------
    // 1️⃣ Методы для получения локаторов полей на странице
    // ---------------------------
    public static Locator urlField(Page page) {
        return page.locator("#model-form input[name='NewCommentForm[url]']");
    }

    public static Locator usernameField(Page page) {
        return page.locator("#model-form input[name='NewCommentForm[name]']");
    }

    public static Locator messageField(Page page) {
        return page.locator("#model-form input[name='NewCommentForm[message]']");
    }

    public static Locator dopInfoButton(Page page) {
        return page.locator("#show_dop_info");
    }

    public static Locator dopInfoField(Page page) {
        return page.locator("#NewCommentForm_dop_info");
    }

    public static Locator submitButton(Page page) {
        return page.locator("#model-form input[type=submit]");
    }

    // ---------------------------
    // 2️⃣ Универсальное заполнение поля
    // ---------------------------
    public static void fillIfExists(Page page, Locator field, String value) {
        if (field.count() > 0 && field.isVisible()) {
            field.waitFor();
            field.click();
            field.fill(value);
            field.blur();
            System.out.println("✅ Заполнено поле: " + value);
        }
    }

    // ---------------------------
    // 3️⃣ Проверка ошибки после заполнения
    // ---------------------------
    public static boolean hasError(Locator field) {
        Locator error = field.locator(
                "xpath=following-sibling::*[1][contains(@class,'errorMessage')]");
        try {
            error.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(500));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}