package org.example.modules.taskstate.handlers;

import com.microsoft.playwright.Page;
import org.example.modules.taskstate.*;

public class UnknownFormHandler implements TaskHandler {

    @Override
    public void handle(Page page) {
            System.out.println("🧩 UNKNOWN handler");
        }
    }
