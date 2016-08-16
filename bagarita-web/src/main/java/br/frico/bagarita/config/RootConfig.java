package br.frico.bagarita.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * Root Configuration
 * Created by Felipe Rico on 8/15/2016.
 */
@Configuration
@ComponentScan("br.frico.bagarita.config")
@PropertySources({
        @PropertySource(value = "classpath:application.properties")
})
public class RootConfig {
}
