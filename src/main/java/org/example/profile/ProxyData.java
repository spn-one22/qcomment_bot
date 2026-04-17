package org.example.profile;

//public class ProxyData {
//
//    public String type; // http, socks5
//
//    public String host;
//    public int port;
//
//    public String username;
//    public String password;
//}


/**
 * Модель прокси
 */
public class ProxyData {

    private String type; // http, socks5

    private String host;
    private int port;

    private String username;
    private String password;

    public ProxyData(String type, String host, int port, String username, String password) {
        this.type = type;
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}