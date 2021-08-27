package com.fnklabs.smsaero.client.core;

import com.fnklabs.smsaero.client.http.HttpClientTransport;
import com.fnklabs.smsaero.client.jackson.JacksonMarshaller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


class SmsAeroClientBuilderTest {
    private SmsAeroClient aeroClient;
    private SmsAeroClientBuilder smsAeroClientBuilder;

    @BeforeEach
    public void setUp() {
        smsAeroClientBuilder = SmsAeroClient.builder();
    }

    @Nested
    public class WithEndpoint {
        @BeforeEach
        public void setUp() {
            smsAeroClientBuilder.setEndpoint("test");

            aeroClient = smsAeroClientBuilder.build();
        }

        @Test
        public void checkEndpoint() {
            assertEquals("test", aeroClient.endpoint);
        }
    }

    @Nested
    public class WithCredentials {
        @BeforeEach
        public void setUp() {
            smsAeroClientBuilder.setCredentials(new Credentials("SMSAERO_USERNAME", "SMSAERO_API_KEY"));

            aeroClient = smsAeroClientBuilder.build();
        }

        @Test
        public void checkCredentials() {
            assertEquals("SMSAERO_USERNAME", aeroClient.credentials.username());
            assertEquals("SMSAERO_API_KEY", aeroClient.credentials.apiKey());
        }
    }

    @Nested
    @ExtendWith(MockitoExtension.class)
    public class WithTransport {
        @Mock
        private Transport transport;

        @BeforeEach
        public void setUp() {
            smsAeroClientBuilder.setTransport(transport);

            aeroClient = smsAeroClientBuilder.build();
        }

        @Test
        public void checkTransport() {
            assertEquals(transport, aeroClient.transport);
        }
    }

    @Nested
    @ExtendWith(MockitoExtension.class)
    public class WithMarshaller {
        @Mock
        private Marshaller marshaller;

        @BeforeEach
        public void setUp() {
            smsAeroClientBuilder.setMarshaller(marshaller);

            aeroClient = smsAeroClientBuilder.build();
        }

        @Test
        public void checkMarshaller() {
            assertEquals(marshaller, aeroClient.marshaller);
        }
    }

    @Nested
    public class WhenCreate {
        @BeforeEach
        public void setUp() {
            smsAeroClientBuilder.setCredentials(new Credentials(System.getenv("SMSAERO_USERNAME"), System.getenv("SMSAERO_API_KEY")));

            aeroClient = smsAeroClientBuilder.build();
        }

        @Test
        public void thenCheckAeroClient() {
            assertNotNull(aeroClient);
        }

        @Nested
        public class AndWithHttpClientTransport {
            @BeforeEach
            public void setUp() throws Exception {
                smsAeroClientBuilder.setTransport(new HttpClientTransport(new JacksonMarshaller()));

                aeroClient = smsAeroClientBuilder.build();
            }

            @Nested
            public class ThenAuthenticate {

                private boolean authenticate;

                @BeforeEach
                public void setUp() {
                    authenticate = aeroClient.authenticate(new Credentials(System.getenv("SMSAERO_USERNAME"), System.getenv("SMSAERO_API_KEY")));
                }

                @Test
                public void thenOk() {
                    assertTrue(authenticate);

                }
            }

            @Nested
            public class ThenTestSend {

                private boolean result;

                @BeforeEach
                public void setUp() {
                    result = aeroClient.sendTest(System.getenv("TEST_PHONE"), System.getenv("TEST_SIGN"), "Text");
                }

                @Test
                public void thenCheckResult() {
                    assertTrue(result);
                }
            }

            @EnabledIfEnvironmentVariable(named = "SEND_REAL_SMS", matches = "true")
            @Nested
            public class ThenSend {

                private boolean result;

                @BeforeEach
                public void setUp() {
                    result = aeroClient.send(System.getenv("TEST_PHONE"), System.getenv("TEST_SIGN"), "Text");
                }

                @Test
                public void thenCheckResult() {
                    assertTrue(result);
                }
            }
        }


    }
}