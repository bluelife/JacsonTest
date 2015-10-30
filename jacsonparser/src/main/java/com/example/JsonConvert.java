package com.example;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by slomka.jin on 2015/10/29.
 */
public class JsonConvert {

    private final ObjectMapper objectMapper;

    public JsonConvert(ObjectMapper mapper,SimpleModule simpleModule){
        this.objectMapper=mapper;
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        configureModule(simpleModule);
        objectMapper.registerModule(simpleModule);

    }

    private void configureModule(SimpleModule module){
        module.addDeserializer(LoginStatus.class, new LoginConvert());
    }
    public <T> T readValue(Reader reader, Class<T> valueType) throws IOException {
        return objectMapper.readValue(reader, valueType);
    }
}
