package com.pkalinov.autopartsmgmtserver.exceptions;

import java.io.Serializable;

public class AutoPartsManagerException extends Exception implements Serializable {

    public static final long serialVersionUID = 1L;

    private int statusCode;

    private String message;

    public AutoPartsManagerException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
