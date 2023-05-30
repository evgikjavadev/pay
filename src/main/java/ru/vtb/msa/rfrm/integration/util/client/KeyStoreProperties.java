package ru.vtb.msa.rfrm.integration.util.client;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;

@Slf4j
@Data
public class KeyStoreProperties {

    private String storeLocation;
    private String storePassword;

    public char[] getStorePasswordCharArray() {
        return storePassword != null ? storePassword.toCharArray() : null;
    }

    public InputStream getStoreInputStream() throws IOException {
        File file = new File(storeLocation);

        if (file.exists()) {
            log.info("Файл с сертификатами существует ");
            return Files.newInputStream((Paths.get(file.getPath())));
        }
        return null;
    }

    public KeyStore loadKeyStore() throws Exception {
        final KeyStore keyStore = keyStoreInstance();
        try (InputStream stream = getStoreInputStream()) {
            keyStore.load(stream, getStorePasswordCharArray());
        }
       catch (Exception ex){
            log.error("Ошибка загрузкой сертификатов {}", ex.getMessage());
       }
        return keyStore;
    }

    private KeyStore keyStoreInstance() throws Exception {
        return KeyStore.getInstance("jks");
    }
}
