package org.example.modules;

import com.microsoft.playwright.*;
import java.util.*;

public class TaskParser {

    private final Page page;

    public TaskParser(Page page) {
        this.page = page;
    }

    public List<FormField> parseAllFields() {

        List<FormField> fields = new ArrayList<>();

        Locator form = page.locator("#model-form");
        form.waitFor();

        // 🔥 берём только логические группы полей
        Locator groups = form.locator(".control-group");

        for (Locator group : groups.all()) {

            try {
                FormField field = parseGroup(group);
                if (field != null) {
                    fields.add(field);
                }
            } catch (Exception e) {
                System.out.println("⚠️ Ошибка парсинга группы: " + e.getMessage());
            }
        }

        return fields;
    }

    private FormField parseGroup(Locator group) {

        // 🔹 label (главный источник смысла)
        String label = null;
        if (group.locator("label").count() > 0) {
            label = clean(group.locator("label").first().textContent());
        }

        // 🔹 поле (input / textarea / select)
        Locator field = group.locator("input, textarea, select").first();

        if (field.count() == 0) return null;

        String name = field.getAttribute("name");
        if (name == null || name.isEmpty()) return null;

        String tag = field.evaluate("e => e.tagName").toString().toLowerCase();

        String type = field.getAttribute("type");
        if (type == null) {
            type = tag.equals("textarea") ? "textarea" : tag;
        }

        // ❌ пропускаем hidden — это системные поля
        if ("hidden".equalsIgnoreCase(type)) return null;

        boolean visible = field.isVisible();
        boolean required = field.evaluate("el => el.required").equals(true);

        String placeholder = field.getAttribute("placeholder");
        String value = field.getAttribute("value");

        List<String> options = new ArrayList<>();
        if ("select".equals(tag)) {
            for (Locator opt : field.locator("option").all()) {
                options.add(clean(opt.textContent()));
            }
        }

        return new FormField(
                name,
                tag,
                type,
                label,
                placeholder,
                value,
                required,
                visible,
                options
        );
    }

    private String clean(String text) {
        if (text == null) return null;
        return text.replace("\n", " ")
                .replace("\r", " ")
                .trim();
    }

    // =========================
    // 📦 MODEL
    // =========================

    public static class FormField {

        public final String name;
        public final String tag;
        public final String type;

        public final String label;
        public final String placeholder;
        public final String value;

        public final boolean required;
        public final boolean visible;

        public final List<String> options;

        public FormField(String name,
                         String tag,
                         String type,
                         String label,
                         String placeholder,
                         String value,
                         boolean required,
                         boolean visible,
                         List<String> options) {

            this.name = name;
            this.tag = tag;
            this.type = type;
            this.label = label;
            this.placeholder = placeholder;
            this.value = value;
            this.required = required;
            this.visible = visible;
            this.options = options;
        }

        @Override
        public String toString() {
            return "FormField{" +
                    "name='" + name + '\'' +
                    ", tag='" + tag + '\'' +
                    ", type='" + type + '\'' +
                    ", label='" + label + '\'' +
                    ", placeholder='" + placeholder + '\'' +
                    ", value='" + value + '\'' +
                    ", required=" + required +
                    ", visible=" + visible +
                    ", options=" + options +
                    '}';
        }
    }
}