package com.fnklabs.smsaero.client.core;

import org.jetbrains.annotations.VisibleForTesting;

import java.util.Collection;

public class SmsAeroClient {
    @VisibleForTesting
    final String endpoint;
    @VisibleForTesting
    final Credentials credentials;
    @VisibleForTesting
    final Marshaller marshaller;
    @VisibleForTesting
    final Transport transport;

    public SmsAeroClient(String endpoint, Credentials credentials, Marshaller marshaller, Transport transport) {
        this.endpoint = endpoint;
        this.credentials = credentials;
        this.marshaller = marshaller;
        this.transport = transport;
    }

    public boolean authenticate(Credentials credentials) {
        return transport.authenticate(endpoint, credentials);
    }

    public boolean send(String number, String sign, String text) {
        return transport.send(endpoint, credentials, number, sign, text);
    }

    public boolean sendTest(String number, String sign, String text) {
        return transport.testSend(endpoint, credentials, number, sign, text);

    }

    public static SmsAeroClientBuilder builder() {
        return new SmsAeroClientBuilder();
    }
}
