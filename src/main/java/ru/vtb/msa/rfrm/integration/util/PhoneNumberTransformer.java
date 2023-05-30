package ru.vtb.msa.rfrm.integration.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PhoneNumberTransformer {
    public static final String START_DIGIT = "7";
    public static final int NORM_LENGTH = 11;

    // можно в будующем сделать для обработки стационарных телефонов, добавлять код региона (но есть ли сейчас такие люди?)

    public String transformer(String number) {
        String st = number.replaceAll("[+()\\s-]", "");
        if (st.length() == 10) {
            return START_DIGIT + st;
        } else if (st.length() == NORM_LENGTH) {
            return START_DIGIT + st.substring(1);
        } else {
            StringBuilder builder = new StringBuilder(START_DIGIT);
            int count = NORM_LENGTH - st.length() - 1;
            builder.append("0".repeat(Math.max(0, count)));
            builder.append(st);
            return builder.toString();
        }
    }
}