package org.example.core;

import com.microsoft.playwright.*;
import java.nio.file.Paths;
import java.util.Arrays;

public class SessionManager {

    private static final String SESSION_FILE = "session.json";

    public static BrowserContext initContext(Browser browser) {

        Browser.NewContextOptions options = new Browser.NewContextOptions()
                .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 Chrome/120 Safari/537.36")
                .setViewportSize(1920, 1080)
                .setLocale("ru-RU")
                .setTimezoneId("Europe/Moscow")
                .setGeolocation(59.93, 30.31)
                .setPermissions(Arrays.asList("geolocation"));

//                .setLocale("ru-RU")
//                .setTimezoneId("Europe/Moscow") // ВАЖНО
//                .setGeolocation(55.75, 37.61)
//                .setPermissions(Arrays.asList("geolocation"));

//                .setLocale("uk-UA") // можно и ru-RU, но uk-UA выглядит нативнее
//                .setTimezoneId("Europe/Kyiv")
//                .setGeolocation(50.45, 30.52)
//                .setPermissions(Arrays.asList("geolocation"));

        // если есть сохранённая сессия — добавляем её
        if (Paths.get(SESSION_FILE).toFile().exists()) {
            options.setStorageStatePath(Paths.get(SESSION_FILE));
        }

        BrowserContext context = browser.newContext(options);



        // 🔥 АНТИДЕТЕКТ (самое важное)
        context.addInitScript("""
        // убираем webdriver (главный палевный флаг)
        Object.defineProperty(navigator, 'webdriver', {
            get: () => undefined
        });

        // подделываем chrome
        window.chrome = {
            runtime: {}
        };

        // языки как у реального пользователя
        Object.defineProperty(navigator, 'languages', {
            get: () => ['ru-RU', 'ru']
        });

        // плагины (иногда проверяют)
        Object.defineProperty(navigator, 'plugins', {
            get: () => [1, 2, 3]
        });
    """);

        return context;
    }

    public static void save(BrowserContext context) {
        context.storageState(
                new BrowserContext.StorageStateOptions()
                        .setPath(Paths.get(SESSION_FILE))
        );
    }
}
