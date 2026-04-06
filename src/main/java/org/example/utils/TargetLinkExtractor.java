package org.example.utils;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class TargetLinkExtractor {

    /**
     * Ищет ссылку "Перейти на сайт заказчика". Если нет — возвращает текущий URL страницы.
     * @param page страница qcomment
     * @return URL для вставки в плейсхолдер
     */

    public static String extractTargetLink(Page page) {

        // ищем первую ссылку с pattern /site/go?url=
        Locator link = page.locator("a[href*='/site/go?url=']").first();

        if (link.count() > 0) {
            String href = link.getAttribute("href");
            if (href != null && href.contains("url=")) {
                String[] parts = href.split("url=");
                if (parts.length >= 2) {
                    String target = parts[1];
                    System.out.println("🔗 Найдена ссылка на сайт заказчика: " + target);
                    return target;
                }
            }
        }

        // ⚠️ Костыль: нет ссылки на сайт заказчика → используем текущий URL задания
        String currentProjectUrl = page.url();
        System.out.println("⚠️ Ссылка на сайт заказчика не найдена, используем URL текущего проекта: " + currentProjectUrl);
        return currentProjectUrl;
    }
}