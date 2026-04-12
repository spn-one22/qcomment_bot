package org.example.modules.taskstate.handlers;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.example.modules.taskstate.TaskHandler;
import org.example.utils.FormFiller;
import org.example.utils.generators.*;

import java.util.Random;

public class MainFormHandler implements TaskHandler {

    @Override
    public void handle(Page page) {

        Random random = new Random();

        System.out.println("🧩 Обработка MAIN_FORM");

        // FILL TARGET LINK
        for (int i = 0; i < 3; i++) {
            String link = Extractor.extractUrl(page);
            FormFiller.fillIfExists(page, FormFiller.urlField(page), link + i);

            if (!FormFiller.hasError(FormFiller.urlField(page))) {
                System.out.println("✅ Поле заполнено успешно");
                break;
            } else {
                System.out.println("❌ Ошибка валидации, пробуем ещё раз");
                page.waitForTimeout(3000);
            }
        }

        // FILL USERNAME
        String approvedUsername = null;
        String baseUsername = Extractor.extractUsername(page);

        for (int i = 0; i < 3; i++) {
            String candidate;

            if (i == 0) {
                // первая попытка — чистый username
                candidate = baseUsername;
            } else {
                // последующие — с рандомным числом
                int randomNum = 1 + random.nextInt(99);
                candidate = baseUsername + randomNum;
            }

            System.out.println("Trying username: " + candidate);

            FormFiller.fillIfExists(page, FormFiller.usernameField(page), candidate);

            if (!FormFiller.hasError(FormFiller.usernameField(page))) {
                System.out.println("✅ Поле заполнено успешно");
                approvedUsername = candidate; // сохраняем именно успешный вариант
                break;
            } else {
                System.out.println("❌ Ошибка валидации, пробуем ещё раз");
                page.waitForTimeout(3000);
            }
        }

        //FILL ACCOUNT LINK
        if (approvedUsername != null) {
            baseUsername = approvedUsername;

            Locator messageField = FormFiller.messageField(page);

            for (int i = 0; i < 3; i++) {
                String candidate;

                if (i == 0) {
                    // первая попытка — просто username
                    candidate = Extractor.extractBaseUrl(Extractor.extractUrl(page)) + baseUsername;
                } else {
                    // дальше — добавляем числа
                    int randomNum = 1 + random.nextInt(99);
                    candidate = Extractor.extractBaseUrl(Extractor.extractUrl(page)) + baseUsername + randomNum;
                }

                System.out.println("Trying accountLink: " + candidate);

                FormFiller.fillIfExists(page, messageField, candidate);

                if (!FormFiller.hasError(messageField)) {
                    System.out.println("✅ Поле заполнено успешно");
                    break;
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
}