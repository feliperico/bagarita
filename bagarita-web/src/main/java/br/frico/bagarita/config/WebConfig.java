package br.frico.bagarita.config;

import br.frico.bagarita.web.controller.CustomObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Web configuration.
 *
 * Created by Felipe Rico on 8/15/2016.
 */
@Configuration
@EnableWebMvc
@ComponentScan("br.frico.bagarita.web.controller")
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private Environment environment;

    @Bean
    public MappingJackson2HttpMessageConverter converter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(customObjectMapper());
        return converter;
    }

    @Bean
    public ObjectMapper customObjectMapper() {
        CustomObjectMapper mapper = new CustomObjectMapper();
        return mapper;
    }

    @Bean
    public SerializationConfig serializationConfig() {
        return customObjectMapper().getSerializationConfig();
    }

    // config static resources
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Don't forget the ending "/" for location or you will hit 404.
        registry.addResourceHandler("/img/**").addResourceLocations("/static/images/");
        registry.addResourceHandler("/js/**").addResourceLocations("/static/js/");
        registry.addResourceHandler("/css/**").addResourceLocations("/static/css/");
    }

}
