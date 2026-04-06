package org.example.utils;

import java.util.Random;

public class UsernameBuilder {

    private static final Random random = new Random();

    private static final String[] firstNames = {
            "alex", "ivan", "sergey", "dmitry", "nikita",
            "anna", "olga", "maria", "irina", "elena"
    };

    private static final String[] lastNames = {
            "ivanov", "petrov", "smirnov", "kovalenko",
            "sidorov", "popov", "volkov", "morozov"
    };

    private static final String[] nicknames = {
            "dark", "pro", "real", "official", "live",
            "best", "top", "master", "cool", "prime"
    };

    /**
     * Генерирует уникальное имя/username
     */
    public static String generate() {
        String first = firstNames[random.nextInt(firstNames.length)];
        String last = lastNames[random.nextInt(lastNames.length)];
        String nick = nicknames[random.nextInt(nicknames.length)];

        int pattern = random.nextInt(8);

        switch (pattern) {
            case 0: return first + last;
            case 1: return first + "." + last;
            case 2: return first + "_" + last;
            case 3: return nick + "_" + first;
            case 4: return first + nick + random.nextInt(100);
            case 5: return first + "." + last + "_" + random.nextInt(100);
            case 6: return first + random.nextInt(100);
            default: return nick + first + last;
        }
    }
}