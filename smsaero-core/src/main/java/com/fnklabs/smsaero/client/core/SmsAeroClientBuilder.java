package com.fnklabs.smsaero.client.core;

import java.util.Optional;

public class SmsAeroClientBuilder {

    private String endpoint;
    private ApiVersion apiVersion = ApiVersion.V2;
    private Credentials credentials;
    private Marshaller marshaller;
    private Transport transport;

    public SmsAeroClientBuilder setEndpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public SmsAeroClientBuilder setApiVersion(ApiVersion apiVersion) {
        this.apiVersion = apiVersion;
        return this;
    }

    public SmsAeroClientBuilder setCredentials(Credentials credentials) {
        this.credentials = credentials;
        return this;
    }

    public SmsAeroClientBuilder setMarshaller(Marshaller marshaller) {
        this.marshaller = marshaller;
        return this;
    }

    public SmsAeroClientBuilder setTransport(Transport transport) {
        this.transport = transport;
        return this;
    }

    public SmsAeroClient build() {
        String endpoint = Optional.ofNullable(this.endpoint)
                                  .or(() -> Optional.ofNullable(apiVersion).map(v -> v.address))
                                  .orElseThrow(() -> new IllegalArgumentException("endpoint is not specified"));

        return new SmsAeroClient(endpoint, credentials, marshaller, transport);
    }
}
