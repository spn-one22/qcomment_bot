package org.example.modules.filter;

import com.microsoft.playwright.Locator;
import org.example.utils.PriceAnalyzer;

public class ProjectFilter {

    public static boolean isSuitable(Locator card) {

        String text = card.innerText();

        // ❌ фильтр: скриншот
        if (text.contains("Требуется скриншот")) {
            return false;
        }

        // ❌ фильтр: видео
        if (text.contains("Длительность:")) {
            return false;
        }

        // ❌ фильтр: активность
        if (text.contains("Проявить активность")) {
            return false;
        }

        // ❌ фильтр: цена

        Locator priceLocator = card.locator(".td2 p").nth(1);
        if (priceLocator.count() == 0) return false;

        String priceText = priceLocator.textContent(); // 🔥 быстрее чем innerText

        try {
            double price = PriceAnalyzer.parse(priceText);
            if (price > 5) return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}