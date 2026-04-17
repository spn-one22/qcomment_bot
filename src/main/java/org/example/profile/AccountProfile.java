package org.example.profile;

import java.nio.file.Path;

public class AccountProfile {

    public String id;

    public Path folder;

    public Path sessionFile;
    public Path proxyFile;
    public Path fingerprintFile;

    public Fingerprint fingerprint;
    public ProxyData proxy;

    public AccountProfile(String id) {
        this.id = id;

        // 📁 папка профиля
        this.folder = Path.of("profiles", id);

        // 📄 файлы
        this.sessionFile = folder.resolve("session.json");
        this.proxyFile = folder.resolve("proxy.json");
        this.fingerprintFile = folder.resolve("fingerprint.json");
    }
}