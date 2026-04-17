package org.example.core;//package org.example.core;
//
//import com.microsoft.playwright.*;
//
//public class BrowserFactory {
//
//    public static Browser create(Playwright playwright) {
//
//        System.out.println("🧪 Обычный запуск браузера");
//
//        return playwright.chromium().launch(
//                new BrowserType.LaunchOptions().setHeadless(false));
//
//    }
//}

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.Proxy;
import org.example.profile.AccountProfile;

public class BrowserFactory {

    public static Browser create(Playwright playwright, AccountProfile profile) {

        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                .setHeadless(false);

        // 🔥 ВОТ ЗДЕСЬ ПРОКСИ
        if (profile.proxy != null) {

            String scheme = profile.proxy.getType().equals("socks5") ? "socks5://" : "http://";

            Proxy proxy = new Proxy(
                    scheme + profile.proxy.getHost() + ":" + profile.proxy.getPort()
            );

            // 🔥 ВОТ ЭТО ТЫ НЕ СДЕЛАЛ
            if (profile.proxy.getUsername() != null) {
                proxy.setUsername(profile.proxy.getUsername());
                proxy.setPassword(profile.proxy.getPassword());
            }

            options.setProxy(proxy);

            System.out.println("🌐 Прокси: " + scheme + profile.proxy.getHost() + ":" + profile.proxy.getPort());
            System.out.println("🔐 Авторизация: " + profile.proxy.getUsername());
        }

        return playwright.chromium().launch(options);
    }
}