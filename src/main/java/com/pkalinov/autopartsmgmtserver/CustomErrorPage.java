package com.pkalinov.autopartsmgmtserver;

import com.pkalinov.autopartsmgmtserver.models.ExceptionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;

public class CustomErrorPage {
    public static ResponseEntity createErrorPage(int errorCode, String errorMessage){
        ExceptionModel model = new ExceptionModel(errorCode, errorMessage);
        return ResponseEntity.status(errorCode).body(model);
    }

    public static ResponseEntity createBadRequestErrorPage() {
        int statusCode = HttpServletResponse.SC_BAD_REQUEST;
        return CustomErrorPage.createErrorPage(statusCode, HttpStatus.resolve(statusCode).getReasonPhrase());
    }
}
