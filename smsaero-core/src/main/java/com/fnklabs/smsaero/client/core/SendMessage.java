package com.fnklabs.smsaero.client.core;

public class SendMessage {
    private final String number;
    private final String sign;
    private final String text;

    public SendMessage(String number, String sign, String text) {
        this.number = number;
        this.sign = sign;
        this.text = text;
    }

    public String getNumber() {
        return number;
    }

    public String getSign() {
        return sign;
    }

    public String getText() {
        return text;
    }
}
