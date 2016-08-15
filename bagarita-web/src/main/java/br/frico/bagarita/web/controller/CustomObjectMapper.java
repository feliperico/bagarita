package br.frico.bagarita.web.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

/**
 * Created by Felipe Rico on 8/15/2016.
 */
@Component
@Primary
public class CustomObjectMapper extends ObjectMapper {

    private static final long serialVersionUID = -5829814108020959847L;

    public CustomObjectMapper() {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(ZonedDateTime.class, CustomInstantSerializer.ZONED_DATE_TIME);
        registerModule(javaTimeModule);
        disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        enable(SerializationFeature.INDENT_OUTPUT);
        setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
}
