package org.example.core;

import com.microsoft.playwright.*;
import org.example.config.Config;

public class AuthModule {

    public static void ensureLogin(Page page, BrowserContext context) throws Exception {

        PageLoader pageloader = new PageLoader(page, 10000, 3);
        pageloader.load(Config.BASE_URL);

        if (page.locator("text=Вход").count() > 0) {
            System.out.println("🔐 Логинись вручную и нажми ENTER...");
            System.in.read();

            SessionManager.save(context);
        }
       // System.out.println(page.content());
    }
}
