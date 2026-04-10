package org.example.modules.taskstate.handlers;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.example.modules.taskstate.TaskHandler;
import org.example.utils.FormFiller;
import org.example.utils.generators.UrlExtractor;

public class MainFormWithSelectHandler implements TaskHandler {

    @Override
    public void handle(Page page) {
        System.out.println("🧩 MAIN_FORM_WITH_SELECT handler");

        for (int i = 0; i < 3; i++) {
            String link = UrlExtractor.extractUrl(page);
            if(i > 0) {link = link + 1;}
            FormFiller.fillIfExists(page, FormFiller.urlField(page), link);

            if (!FormFiller.hasError(FormFiller.urlField(page))) {
                System.out.println("✅ Поле заполнено успешно");
                break;
            } else {
                System.out.println("❌ Ошибка валидации, пробуем ещё раз");
                page.waitForTimeout(3000);
            }
        }
        for (int i = 0; i < 3; i++) {
            String link = "@username" + (i + 1);
            FormFiller.fillIfExists(page, FormFiller.usernameField(page), link);

            if (!FormFiller.hasError(FormFiller.usernameField(page))) {
                System.out.println("✅ Поле заполнено успешно");
                break;
            } else {
                System.out.println("❌ Ошибка валидации, пробуем ещё раз");
                page.waitForTimeout(3000);
            }
        }
        for (int i = 0; i < 3; i++) {
            String link = "example.com/@username" + (i + 1);
            FormFiller.fillIfExists(page, FormFiller.messageField(page), link);

            if (!FormFiller.hasError(FormFiller.messageField(page))) {
                System.out.println("✅ Поле заполнено успешно");
                break;
            } else {
                System.out.println("❌ Ошибка валидации, пробуем ещё раз");
                page.waitForTimeout(3000);
            }
        }

        Locator dopInfoBtn = FormFiller.dopInfoButton(page);
        if (dopInfoBtn.isVisible()) {
            dopInfoBtn.click();
            FormFiller.fillIfExists(page, FormFiller.dopInfoField(page), "Доп инфа");
        }

        Locator submitBtn = FormFiller.submitButton(page);
        submitBtn.waitFor();
        page.waitForFunction(
                "!document.querySelector('#model-form input[type=submit]').disabled"
        );
        submitBtn.click();
        System.out.println("📤 Форма отправлена");
        submitBtn.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.DETACHED));
    }
}