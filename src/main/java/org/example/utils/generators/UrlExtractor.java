package org.example.utils.generators;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.net.URI;
import java.util.Random;

public class UrlExtractor {

    // ---------------------------
    // 1️⃣ Получить ссылку (основная + fallback)
    // ---------------------------
    public static String extractUrl(Page page) {

        // 🔹 Вариант 1 — основная ссылка
        Locator mainLink = page.locator("a[href*='site/go?url=']").first();

        if (mainLink.count() > 0) {
            String href = mainLink.getAttribute("href");

            if (href != null && href.contains("url=")) {
                String url = href.split("url=")[1];
                System.out.println("✅ Найдена основная ссылка: " + url);
                return url;
            }
        }

        // 🔹 Вариант 2 — список ссылок (fallback)
        Locator links = page.locator(".one_page_url a[href*='site/go?url=']");
        int count = links.count();

        if (count > 0) {
            int randomIndex = new Random().nextInt(count);
            Locator randomLink = links.nth(randomIndex);

            String href = randomLink.getAttribute("href");

            if (href != null && href.contains("url=")) {
                String url = href.split("url=")[1];
                System.out.println("🎲 Выбрана случайная ссылка: " + url);
                return url;
            }
        }

        System.out.println("❌ Ссылки не найдены");
        return null;
    }

    // ---------------------------
    // 2️⃣ Получить чистый домен (base URL)
    // ---------------------------
    public static String extractBaseUrl(String fullUrl) {
        try {
            URI uri = new URI(fullUrl);
            return uri.getScheme() + "://" + uri.getHost() + "/";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
//
//        // ⚠️ Костыль: нет ссылки на сайт заказчика → используем текущий URL задания
//        String currentProjectUrl = page.url();
//        System.out.println("⚠️ Ссылка на сайт заказчика не найдена, используем URL текущего проекта: " + currentProjectUrl);
//        return currentProjectUrl;
