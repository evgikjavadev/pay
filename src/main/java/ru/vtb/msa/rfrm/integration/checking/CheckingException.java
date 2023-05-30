package ru.vtb.msa.rfrm.integration.checking;



public class CheckingException extends RuntimeException {

    private final CheckingExceptionType type;

    public CheckingException(CheckingExceptionType type, String message) {
        super(message);
        this.type = type;
    }

    public CheckingExceptionType getType() {
        return this.type;
    }
}