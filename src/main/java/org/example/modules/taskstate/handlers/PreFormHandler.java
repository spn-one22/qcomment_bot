package org.example.modules.taskstate.handlers;

import com.microsoft.playwright.Page;
import org.example.modules.taskstate.TaskHandler;

public class PreFormHandler implements TaskHandler {

    @Override
    public void handle(Page page) {
        System.out.println("🧩 PRE_FORM handler");
    }
}