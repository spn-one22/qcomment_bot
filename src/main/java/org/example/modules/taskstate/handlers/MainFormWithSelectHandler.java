package org.example.modules.taskstate.handlers;

import com.microsoft.playwright.Page;
import org.example.modules.taskstate.TaskHandler;

public class MainFormWithSelectHandler implements TaskHandler {

    @Override
    public void handle(Page page) {
        System.out.println("🧩 MAIN_FORM_WITH_WALL handler");
    }
}