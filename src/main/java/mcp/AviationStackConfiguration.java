package mcp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rest.AviationStackClient;

import rest.AviationStackConfig;

/***
 * Spring will automatically:
 *
 * Create the AviationStackClient bean
 * Detect that AviationStackTools needs it in its constructor
 * Inject it automatically
 */
@Configuration
public class AviationStackConfiguration {

    @Bean
    public AviationStackClient aviationStackClient() throws Exception {
        return new AviationStackClient(AviationStackConfig.loadApiKey());
    }
}