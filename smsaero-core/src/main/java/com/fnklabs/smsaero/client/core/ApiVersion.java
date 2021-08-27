package com.fnklabs.smsaero.client.core;

public enum ApiVersion {
    V2("v2", "https://gate.smsaero.ru/v2"),
    ;
    public final String val;
    public final String address;

    ApiVersion(String val, String address) {
        this.val = val;
        this.address = address;
    }


}
