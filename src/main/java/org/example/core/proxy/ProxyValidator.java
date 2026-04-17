package org.example.core.proxy;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.example.profile.ProxyData;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.time.Duration;

/**
 * Проверяет прокси и возвращает IP,
 * который виден через этот прокси
 */
public class ProxyValidator {

    /**
     * Получает IP через прокси
     */
    public static String getIpThroughProxy(ProxyData proxy) {

        try {

            // 1. Создаём Java Proxy объект
            Proxy proxyObj = new Proxy(
                    Proxy.Type.HTTP,
                    new InetSocketAddress(proxy.getHost(), proxy.getPort())
            );

            // 2. Билдер HTTP клиента
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .proxy(proxyObj)
                    .connectTimeout(Duration.ofSeconds(10))
                    .readTimeout(Duration.ofSeconds(10));

            // 3. Если есть логин/пароль — добавляем авторизацию
            if (proxy.getUsername() != null && proxy.getPassword() != null) {

                builder.proxyAuthenticator((route, response) -> {

                    String credential = Credentials.basic(
                            proxy.getUsername(),
                            proxy.getPassword()
                    );

                    return response.request().newBuilder()
                            .header("Proxy-Authorization", credential)
                            .build();
                });
            }

            OkHttpClient client = builder.build();

            // 4. Запрос к сервису IP
            Request request = new Request.Builder()
                    .url("https://api.ipify.org?format=json")
                    .build();

            // 5. Выполняем запрос через прокси
            Response response = client.newCall(request).execute();

            // 6. Проверка ответа
            if (!response.isSuccessful()) {
                throw new RuntimeException("Proxy request failed: HTTP " + response.code());
            }

            // 7. Получаем ответ
            String body = response.body().string();

            // 8. Парсим IP (упрощённо)
            String ip = body.replaceAll("[^0-9.]", "");

            System.out.println("🧩 Proxy IP: " + ip);

            return ip;

        } catch (Exception e) {
            System.out.println("❌ Proxy failed: " + e.getMessage());
            return null;
        }
    }
}