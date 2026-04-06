# Playwright Bot (Java)

## Как запустить

1. Установи JDK 17+
2. Установи Maven
3. В терминале в корне проекта:

```
mvn exec:java -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install"
mvn compile exec:java -Dexec.mainClass=org.example.Main
```

## Первый запуск
- Откроется браузер
- Войди вручную
- Нажми ENTER в консоли
- Сессия сохранится в session.json

## Следующие запуски
- Бот сразу работает без логина
