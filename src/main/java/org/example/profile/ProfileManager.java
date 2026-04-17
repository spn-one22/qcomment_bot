package org.example.profile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ProfileManager {

    public static AccountProfile create(String id) {

        AccountProfile profile = new AccountProfile(id);

        profile.id = id;

        profile.folder = Paths.get("profiles", id);

        profile.sessionFile = profile.folder.resolve("session.json");
        profile.proxyFile = profile.folder.resolve("proxy.json");
        profile.fingerprintFile = profile.folder.resolve("fingerprint.json");

        // создаём папку профиля
        try {
            Files.createDirectories(profile.folder);
        } catch (Exception e) {
            throw new RuntimeException("Не удалось создать папку профиля", e);
        }

        return profile;
    }

    public static boolean hasSession(AccountProfile profile) {
        return profile.sessionFile.toFile().exists();
    }
}