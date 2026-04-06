package org.example.modules;

import com.microsoft.playwright.Page;
import org.example.modules.taskstate.*;


public class TaskProcessor {

    private final Page page;

    public TaskProcessor(Page page) {
        this.page = page;
    }

    public void processTask() {
        // 1. Определяем состояние
        TaskState state = new TaskStateResolver(page).detectState();

        // 2. Берём handler
        TaskHandler handler = TaskHandlerFactory.getHandler(state);

        // 3. Запускаем обработку
        handler.handle(page);

        // 4. Можно добавить логирование/отчёт
        System.out.println("✅ Задача обработана: " + state);
    }
}