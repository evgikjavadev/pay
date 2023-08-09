package ru.vtb.msa.rfrm.log.mask;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class MaskAllNumberFields {
    private static final String NUMBER = "\\\"number\\\"\\s*:\\s*\\\"(.*?)\\\"";
    private static final String ENTITY_ID = "\\\"entityId\\\"\\s*:\\s*\\\"(.*?)\\\"";

    private static final List<String> LIST_NAMES = List.of(NUMBER, ENTITY_ID);

    public static void doMaskNumberFields(StringBuilder str) {
        maskAllNumberFields(str);
    }
    private static StringBuilder maskAllNumberFields(StringBuilder str) {

        Pattern multilinePattern = Pattern.compile(String.join("|", MaskAllNumberFields.LIST_NAMES), Pattern.MULTILINE);
        Matcher matcher = multilinePattern.matcher(str);

        while (matcher.find()) {
            IntStream.rangeClosed(1, matcher.groupCount()).forEach(elem -> {
                if (Objects.nonNull(matcher.group(elem))) {
                    int lenghtInit = matcher.group(elem).length();
                    int lenghtMask = (int) Math.ceil(matcher.group(elem).length() * 0.5);
                    int nonMaskLenght = lenghtInit - lenghtMask;
                    String subStr1 = matcher.group(elem).substring(0, nonMaskLenght);
                    String subStr2 =  matcher.group(elem).substring(nonMaskLenght).replaceAll(".", "*");
                    String res = subStr1 + subStr2;
                    str.replace(matcher.start(elem), matcher.end(elem), res);
                }
            });
        }
        return str;
    }
}
