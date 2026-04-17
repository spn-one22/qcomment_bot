package org.example.core.proxy;

import org.example.profile.AccountProfile;
import org.example.profile.ProfileManager;
import org.example.profile.ProfileLoader;

public class ProxyTestMain {

    public static void main(String[] args) throws Exception {

        // 1. создаём профиль через ProfileManager
        AccountProfile profile = ProfileManager.create("spn_one2");

        // 2. загружаем данные (proxy + fingerprint)
        ProfileLoader.load(profile);

        // 3. проверяем proxy
        if (profile.proxy == null) {
            System.out.println("⚠️ Proxy not set");
            return;
        }

        String proxyIp = ProxyValidator.getIpThroughProxy(profile.proxy);

        if (proxyIp == null) {
            System.out.println("❌ Proxy is NOT working");
        } else {
            System.out.println("🧩 Proxy is working");
            System.out.println("🌐 Proxy IP: " + proxyIp);
        }
    }
}