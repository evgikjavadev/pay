package ru.vtb.msa.rfrm.integration.util;

import ru.vtb.msa.rfrm.integration.HttpStatusException;

public class MyExceptionHandler {

    private final HttpStatusException httpStatusException;


    public MyExceptionHandler(HttpStatusException httpStatusException) {
        this.httpStatusException = httpStatusException;
    }

    private void printException() {
        System.out.println("myExceptionHandlerStatus ==== " + httpStatusException.getStatus());
    }
}
