package org.example.modules.taskstate;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.ArrayList;
import java.util.List;

public class TaskStateResolver {

    private final Page page;

    public TaskStateResolver(Page page) {
        this.page = page;
    }

    public TaskState detectState() {

        // 🔹 1. ждём появления ЛЮБОГО известного элемента (из всех состояний)
        String waitSelector = buildWaitSelector();

        page.waitForSelector(waitSelector,
                new Page.WaitForSelectorOptions()
                        .setState(WaitForSelectorState.ATTACHED)); // 👈 только наличие в DOM

        // 🔹 2. перебираем все состояния
        for (TaskState state : TaskState.values()) {

            String[] selectors = state.getSelectors();

            // пропускаем UNKNOWN (у него нет селекторов)
            if (selectors == null || selectors.length == 0) {
                continue;
            }

            boolean allPresent = true;

            // 🔹 3. проверяем: ВСЕ ли селекторы этого состояния есть в DOM
            for (String selector : selectors) {

                if (page.locator(selector).count() == 0) {
                    allPresent = false;
                    break;
                }
            }

            // 🔹 4. если все селекторы найдены — это текущее состояние
            if (allPresent) {
                System.out.println("📍 Найдено состояние: " + state);
                return state;
            }
        }

        // 🔹 5. если ничего не подошло
        return TaskState.UNKNOWN;
    }

    // 🔹 собираем ВСЕ селекторы из enum в одну строку
    private String buildWaitSelector() {

        List<String> selectors = new ArrayList<>();

        for (TaskState state : TaskState.values()) {

            String[] stateSelectors = state.getSelectors();

            if (stateSelectors == null) continue;

            for (String selector : stateSelectors) {
                selectors.add(selector);
            }
        }

        // "#lmodel-form, #model-form, .wall.wall-no-margin"
        return String.join(", ", selectors);
    }
}