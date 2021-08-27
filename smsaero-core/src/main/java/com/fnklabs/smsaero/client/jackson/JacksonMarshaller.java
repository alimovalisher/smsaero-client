package com.fnklabs.smsaero.client.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fnklabs.smsaero.client.core.Marshaller;

import java.io.IOException;

public class JacksonMarshaller implements Marshaller {
    private final ObjectMapper objectMapper;

    public JacksonMarshaller() {
        objectMapper = new ObjectMapper();
    }

    public JacksonMarshaller(ObjectMapper objectMapper) {this.objectMapper = objectMapper;}

    @Override
    public <T> byte[] serialize(T obj) throws IOException {
        return objectMapper.writeValueAsBytes(obj);
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> cls) throws IOException {
        return objectMapper.readValue(data, cls);
    }
}
