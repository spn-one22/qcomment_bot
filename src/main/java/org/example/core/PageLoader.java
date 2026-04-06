package org.example.core;

import com.microsoft.playwright.*;

import com.microsoft.playwright.options.WaitUntilState;

public class PageLoader {
    private final Page page;
    private final int timeout; // в миллисекундах
    private final int maxRetries;

    public PageLoader(Page page, int timeout, int maxRetries) {
        this.page = page;
        this.timeout = timeout;
        this.maxRetries = maxRetries;
    }

    /**
     * Загружает страницу с retry и fallback логикой.
     */

    public void load(String url) {
        int attempt = 0;
        while (attempt < maxRetries) {
            try {
                System.out.println("Попытка " + (attempt + 1) + " загрузки: " + url);

                page.navigate(
                        url,
                        new Page.NavigateOptions()
                                .setWaitUntil(WaitUntilState.DOMCONTENTLOADED)
                                .setTimeout(timeout)
                );

                // Проверка на about:blank
                if ("about:blank".equals(page.url())) {
                    throw new RuntimeException("Страница вернула about:blank");
                }

                // Если дошли сюда — страница успешно загружена
                System.out.println("Страница загружена: " + page.url());
                return;

            } catch (RuntimeException e) {
                System.out.println("Ошибка загрузки: " + e.getMessage());
                attempt++;
                if (attempt >= maxRetries) {
                    System.out.println("Превышено количество попыток загрузки");
                    // Тут можно добавить fallback, например логирование или уведомление
                } else {
                    System.out.println("Повторная попытка...");
                }
            }
        }
    }
}