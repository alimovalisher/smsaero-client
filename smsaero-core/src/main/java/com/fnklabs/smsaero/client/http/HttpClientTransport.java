package com.fnklabs.smsaero.client.http;

import com.fnklabs.smsaero.client.core.Credentials;
import com.fnklabs.smsaero.client.core.Marshaller;
import com.fnklabs.smsaero.client.core.Message;
import com.fnklabs.smsaero.client.core.SendMessage;
import com.fnklabs.smsaero.client.core.Transport;
import org.apache.commons.io.IOUtils;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class HttpClientTransport implements Transport {
    public static final Logger log = LoggerFactory.getLogger(HttpClientTransport.class);
    private final Marshaller marshaller;
    private final CloseableHttpClient httpClient;
    public static final String HTTP_AGENT = "FnkLabs/0.1 (compatible; FnkLabs/0.1; +http://fnklabs.com/bot.html)";

    public HttpClientTransport(Marshaller marshaller, CloseableHttpClient httpClient) {
        this.marshaller = marshaller;
        this.httpClient = httpClient;
    }

    public HttpClientTransport(Marshaller marshaller) throws Exception {
        this.marshaller = marshaller;
        this.httpClient = HttpClients.custom()
                                     .setConnectionManager(new PoolingHttpClientConnectionManager())
                                     .setUserAgent(HTTP_AGENT)
                                     .build();

    }

    @Override
    public boolean authenticate(String endpoint, Credentials credentials) {

        HttpPost httpPost = new HttpPost("%s/auth".formatted(endpoint));
        httpPost.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON);
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON);
        String base64String = Base64.getEncoder()
                                    .encodeToString(
                                            ("%s:%s".formatted(credentials.username(), credentials.apiKey())).getBytes(StandardCharsets.UTF_8));
        ;
        String value = "Basic %s".formatted(base64String);
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, value);
        try (CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpPost)) {
            byte[] response = IOUtils.toByteArray(closeableHttpResponse.getEntity().getContent());
            log.debug("response: {}", IOUtils.toString(response));

            if (closeableHttpResponse.getCode() == HttpStatus.SC_OK) {
                Message msg = marshaller.deserialize(response, Message.class);

                return msg.isSuccess();
            }

            return false;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public boolean send(String endpoint, Credentials credentials, String number, String sign, String text) {
        HttpPost httpPost = new HttpPost("%s/sms/send".formatted(endpoint));
        httpPost.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON);
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON);


        String base64String = Base64.getEncoder()
                                    .encodeToString(
                                            ("%s:%s".formatted(credentials.username(), credentials.apiKey())).getBytes(StandardCharsets.UTF_8));
        ;
        String value = "Basic %s".formatted(base64String);
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, value);

        SendMessage sendMessage = new SendMessage(number, sign, text);

        try {
            String strMsg = new String(marshaller.serialize(sendMessage));

            log.debug("Send msg: {}", strMsg);
            httpPost.setEntity(new StringEntity(strMsg));


            try (CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpPost)) {
                byte[] response = IOUtils.toByteArray(closeableHttpResponse.getEntity().getContent());
                log.debug("response: {}", IOUtils.toString(response));

                if (closeableHttpResponse.getCode() == HttpStatus.SC_OK) {
                    Message msg = marshaller.deserialize(response, Message.class);

                    return msg.isSuccess();
                }

                return false;
            } catch (Exception e) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean testSend(String endpoint, Credentials credentials, String number, String sign, String text) {
        HttpPost httpPost = new HttpPost("%s/sms/testsend".formatted(endpoint));
        httpPost.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON);
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON);


        String base64String = Base64.getEncoder()
                                    .encodeToString(
                                            ("%s:%s".formatted(credentials.username(), credentials.apiKey())).getBytes(StandardCharsets.UTF_8));
        ;
        String value = "Basic %s".formatted(base64String);
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, value);

        SendMessage sendMessage = new SendMessage(number, sign, text);

        try {
            String strMsg = new String(marshaller.serialize(sendMessage));

            log.debug("Send msg: {}", strMsg);
            httpPost.setEntity(new StringEntity(strMsg));


            try (CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpPost)) {
                byte[] response = IOUtils.toByteArray(closeableHttpResponse.getEntity().getContent());
                log.debug("response: {}", IOUtils.toString(response));

                if (closeableHttpResponse.getCode() == HttpStatus.SC_OK) {
                    Message msg = marshaller.deserialize(response, Message.class);

                    return msg.isSuccess();
                }

                return false;
            } catch (Exception e) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void close() throws Exception {

    }
}
