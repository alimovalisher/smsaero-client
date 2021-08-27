# smsaero-client

[SMS Aero](https://smsaero.ru) java client allow to send sms

# requirements 

```
java-16
```

# Get

```gradle
    implementation "com.fnklabs.smsaero:smsaero-core:0.1.0"
```

# Send SMS

```java
import com.fnklabs.smsaero.client.core.SmsAeroClientBuilder;

public class Test {
    public void main(String[] args) {
        SmsAeroClient smsAeroClient = SmsAeroClient.builder()
                                                   .setCredentials(new Credentials("SMSAERO_USERNAME", "SMSAERO_API_KEY"))
                                                   .setTransport(new HttpClientTransport(new JacksonMarshaller()))
                                                   .build();

        if (!smsAeroClient.sendTest("70000000000", "Sign", "Text")) {
            throw new RuntimeException("can't sent sms");
        }
    }
}

        
```