package org.example.modules;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.example.config.Config;
import org.example.core.PageLoader;
import org.example.modules.filter.ProjectFilter;
import org.example.utils.PriceAnalyzer;

public class ProjectModule {
    private final Page page;

    public ProjectModule(Page page) {
        this.page = page;
    }

    public String getFirstValidProject() {

        PageLoader basePageLoader = new PageLoader(page, 10000, 3);
        basePageLoader.load(Config.BASE_URL);

        page.waitForSelector("article.single");

        Locator cards = page.locator("article.single");

        int count = cards.count();
        System.out.println("📊 Карточек найдено: " + count);

        for (int i = 0; i < count; i++) {
            Locator card = cards.nth(i);

            if (!ProjectFilter.isSuitable(card)) {
                continue;
            }

            // 🔗 4. ссылка
            Locator link = card.locator("a[href*='/author/project/']");

            if (link.count() > 0) {

                String href = link.first().getAttribute("href");

                if (href != null) {

                    String result = "https://qcomment.com" + href;

                    System.out.println("✅ Нашли подходящий проект");
                    System.out.println("Цена: " + PriceAnalyzer.parse(card.locator(".td2 p").nth(1).textContent()) );
                    return result;
                }
            }
        }
        return null;

    }
    public void takeTask() {
        page.waitForSelector("#turn_ok");
        page.click("#turn_ok");
        System.out.println("Нажимаем кнопку принять задачу");
    }
    public void waitForTaskState() {

        // 🔹 ждём исчезновение кнопки (старого состояния)
        page.waitForSelector("#turn_ok",
                new Page.WaitForSelectorOptions().setState(WaitForSelectorState.DETACHED));
        System.out.println("✅ Задача взята, DOM обновлён");
    }
}