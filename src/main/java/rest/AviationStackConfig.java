package rest;

import java.io.InputStream;
import java.util.Properties;

public final class AviationStackConfig {

    private static final String KEY_NAME = "aviationstack.api.key";
    private static final String ENV_NAME = "AVIATIONSTACK_API_KEY";

    private AviationStackConfig() {}

    public static String loadApiKey() {
        // Environment variable (highest priority)
        String env = System.getenv(ENV_NAME);
        if (env != null && !env.isBlank()) {
            return env;
        }

        // application.properties from classpath
        Properties props = new Properties();
        try (InputStream is = AviationStackConfig.class
                .getClassLoader()
                .getResourceAsStream("application.properties")) {

            if (is != null) {
                props.load(is);
                String key = props.getProperty(KEY_NAME);
                if (key != null && !key.isBlank()) {
                    return key;
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load application.properties", e);
        }

        // Fail fast
        throw new IllegalStateException(
                "Aviationstack API key not found. " +
                        "Set AVIATIONSTACK_API_KEY or aviationstack.api.key"
        );
    }
}