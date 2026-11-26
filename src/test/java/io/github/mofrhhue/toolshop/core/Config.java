package io.github.mofrhhue.toolshop.core;

import java.time.Duration;

/** Settings that can be changed from the command line, e.g. mvn test -Dheadless=false */
public final class Config {

    private Config() {
    }

    public static String baseUrl() {
        return System.getProperty("baseUrl", "https://practicesoftwaretesting.com");
    }

    public static boolean headless() {
        return Boolean.parseBoolean(System.getProperty("headless", "true"));
    }

    public static Duration timeout() {
        return Duration.ofSeconds(Long.parseLong(System.getProperty("timeoutSeconds", "10")));
    }
}
