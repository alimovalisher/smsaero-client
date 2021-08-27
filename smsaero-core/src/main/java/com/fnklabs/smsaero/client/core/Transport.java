package com.fnklabs.smsaero.client.core;


import java.util.Collection;

/**
 * Smsaero transport interface that provide smsaero operations
 */
public interface Transport extends AutoCloseable {
    boolean authenticate(String endpoint, Credentials credentials);

    boolean send(String endpoint, Credentials credentials, String number, String sign, String text);

    boolean testSend(String endpoint, Credentials credentials, String number, String sign, String text);
}
