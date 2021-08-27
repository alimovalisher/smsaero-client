package com.fnklabs.smsaero.client.core;

import java.io.IOException;

public interface Marshaller {
    /**
     * Serialize data
     *
     * @param obj obj to serialize
     * @param <T> obj type
     *
     * @return serialized data
     */
    <T> byte[] serialize(T obj) throws IOException;

    /**
     * Deserialize data from bytes
     *
     * @param data serialized data
     * @param <T>  Data type
     *
     * @return Deserialized obj
     */
    <T> T deserialize(byte[] data, Class<T> cls) throws IOException;
}
