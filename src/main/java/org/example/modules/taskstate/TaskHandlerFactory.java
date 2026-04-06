package org.example.modules.taskstate;

import org.example.modules.taskstate.handlers.*;

import java.util.EnumMap;
import java.util.Map;

public class TaskHandlerFactory {

    private static final Map<TaskState, TaskHandler> handlers = new EnumMap<>(TaskState.class);

    static {
        handlers.put(TaskState.PRE_FORM, new PreFormHandler());
        handlers.put(TaskState.MAIN_FORM, new MainFormHandler());
        handlers.put(TaskState.MAIN_FORM_WITH_SELECT, new MainFormWithSelectHandler());
        handlers.put(TaskState.PRE_FORM_WITH_SELECT, new PreFormWithSelectHandler());
        handlers.put(TaskState.UNKNOWN, new UnknownFormHandler());
    }

    public static TaskHandler getHandler(TaskState state) {
        return handlers.getOrDefault(state,
                page -> System.out.println("⚠️ SUPER UNKNOWN STATE: " + state));
    }
}