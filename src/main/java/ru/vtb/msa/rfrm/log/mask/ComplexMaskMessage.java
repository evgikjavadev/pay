package ru.vtb.msa.rfrm.log.mask;

public class ComplexMaskMessage {
    public static String maskMessage(String message) {

        StringBuilder sb = new StringBuilder(message);

        MaskAllNumberFields.doMaskNumberFields(sb);

        return sb.toString().replace("&", "");
    }
}
