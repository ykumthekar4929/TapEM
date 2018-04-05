package edu.uta.se1.team6.tapem.Helpers;

import java.io.Serializable;

/**
 * Created by yashodhan on 3/23/18.
 */

public class RequestStatus implements Serializable{
    private String errorCode;
    private String errorMessage;
    public String getErrorCode() {
        return errorCode;
    }
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setMessage(String message) {
        this.errorMessage = message;
    }
}
