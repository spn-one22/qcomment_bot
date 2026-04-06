package org.example.modules.taskstate;

public enum TaskState {
    MAIN_FORM_WITH_SELECT("#model-form", ".wall.wall-no-margin"),
    PRE_FORM_WITH_SELECT("#lmodel-form", ".wall.wall-no-margin"),
    PRE_FORM("#lmodel-form"),
    MAIN_FORM("#model-form"),
    UNKNOWN(); // без селекторов

    private final String[] selectors;

    TaskState(String... selectors) {
        this.selectors = selectors;
    }

    public String[] getSelectors() {
        return selectors;
    }
}

