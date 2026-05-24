package common.ssl;

import io.netty.handler.ssl.ClientAuth;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;

public class SslConfig {
    private SslConfig() {}

    private static KeyStore loadKeyStore(File file, char[] password) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        try (FileInputStream fis = new FileInputStream(file)) {
            keyStore.load(fis, password);
        }

        return keyStore;
    }

    private static SslBundle createSslBundle(File file, String password) throws Exception {
        char[] passwordChars = password.toCharArray();
        KeyStore keyStore = loadKeyStore(file, passwordChars);

        // dostarczenie certyfikatu serwera klientowi
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(keyStore, passwordChars);

        // weryfikacja certyfikatu klienta
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(keyStore);

        return new SslBundle(keyStore, kmf, tmf);
    }

    public static SslContext createSslClientContext(File keyStoreFile, String password) throws Exception {
        SslBundle sslBundle = createSslBundle(keyStoreFile, password);

        return SslContextBuilder.forClient()
                .keyManager(sslBundle.kmf())
                .trustManager(sslBundle.tmf())
                .build();
    }

    public static SslContext createSslServerContext(File keyStoreFile, String password) throws Exception {
        SslBundle sslBundle = createSslBundle(keyStoreFile, password);

        return SslContextBuilder.forServer(sslBundle.kmf())
                .trustManager(sslBundle.tmf())
                .clientAuth(ClientAuth.REQUIRE)
                .build();
    }
}
