package org.example;

import com.microsoft.playwright.*;
import org.example.core.*;
import org.example.modules.*;

import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        long start = System.currentTimeMillis();

        try (Playwright playwright = Playwright.create()) {
            Browser browser = BrowserFactory.create(playwright);
            BrowserContext context = SessionManager.initContext(browser);
            Page page = context.newPage();

//            // Блок логирования - начало
//            page.onResponse(response -> {
//                System.out.println("Response: " + response.status() + " " + response.url());
//            });
//
//            page.onRequestFailed(request -> {
//                System.out.println("FAILED: " + request.url());
//            });
//
//            page.onConsoleMessage(msg -> {
//                System.out.println("Console: " + msg.text());
//            });
//
//            page.onRequest(request -> {
//                System.out.println("Request: " + request.method() + " " + request.url());
//            });
//
//            // Блок логирования - конец

            AuthModule.ensureLogin(page, context);

            ProjectModule project = new ProjectModule(page);

            // 👉 получаем проекты
            List<String> projects = Collections.singletonList(project.getFirstValidProject());
            System.out.println("📦 Найдено проектов: " + projects.size());
            System.out.println("Затрачено времени на поиск: " + (System.currentTimeMillis() - start)/1000 + "секунд");

            // 👉 выводим ссылки
            for (String link : projects) {
                System.out.println(link);
            }

            // 👉 пока просто берём первый
            if (projects.isEmpty()) {
                System.out.println("❌ Нет проектов");
                return;
            }

            String projectUrl = projects.get(0);
            System.out.println("📌 Берём проект: " + projectUrl);

            PageLoader loader = new PageLoader(page, 10000, 1);
            loader.load(projectUrl);

            project.takeTask();

            project.waitForTaskState();

            TaskProcessor processor = new TaskProcessor(page);
            processor.processTask();



        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            long end = System.currentTimeMillis();
            System.out.printf("⏱ Время работы: %.2f секунд%n", (end - start)/1000.0);
        }

    }
}

