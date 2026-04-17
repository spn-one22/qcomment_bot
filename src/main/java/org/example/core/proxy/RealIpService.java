package org.example.core.proxy;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.time.Duration;

/**
 * Сервис для получения РЕАЛЬНОГО IP адреса машины.
 *
 * ❗ ВАЖНО:
 * - Работает БЕЗ прокси
 * - Используется ДО запуска браузера
 * - Нужен для проверки, что прокси не "палит" наш IP
 */
public class RealIpService {

    // Кэшируем IP, чтобы не делать лишние запросы
    private static String cachedIp;

    /**
     * Получить реальный IP (с кешированием)
     */
    public static String getRealIp() {

        // Если уже получали — возвращаем из кеша
        if (cachedIp != null) {
            return cachedIp;
        }

        try {
            // Создаём HTTP клиент БЕЗ прокси
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(Duration.ofSeconds(10)) // таймаут на подключение
                    .build();

            // Запрос к сервису, который возвращает наш IP
            Request request = new Request.Builder()
                    .url("https://api.ipify.org?format=json")
                    .build();

            // Выполняем запрос
            Response response = client.newCall(request).execute();

            // Проверяем, что ответ успешный
            if (!response.isSuccessful()) {
                throw new RuntimeException("Failed to get real IP. HTTP code: " + response.code());
            }

            // Тело ответа, например: {"ip":"1.2.3.4"}
            String body = response.body().string();

            // Быстрый парсинг (вырезаем всё кроме цифр и точек)
            String ip = body.replaceAll("[^0-9.]", "");

            // Сохраняем в кеш
            cachedIp = ip;

            System.out.println("🌍 Real IP: " + ip);

            return ip;

        } catch (Exception e) {
            throw new RuntimeException("❌ Cannot determine real IP", e);
        }
    }
}