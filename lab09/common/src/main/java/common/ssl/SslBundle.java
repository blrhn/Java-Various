package common.ssl;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import java.security.KeyStore;

public record SslBundle(
        KeyStore keyStore,
        KeyManagerFactory kmf,
        TrustManagerFactory tmf) {}
