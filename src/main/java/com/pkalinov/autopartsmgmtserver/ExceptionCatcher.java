package com.pkalinov.autopartsmgmtserver;

import com.pkalinov.autopartsmgmtserver.exceptions.AutoPartsManagerException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class ExceptionCatcher {

    @ExceptionHandler(AutoPartsManagerException.class)
    //custom exception for special errors
    public ResponseEntity autoPartsManagerExceptionCatcher(AutoPartsManagerException apme){
        return CustomErrorPage.createErrorPage(apme.getStatusCode(), apme.getMessage());
    }

    //exception for malformed JSON or not JSON
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity httpMessageNotReadableExceptionCatcher(HttpMessageNotReadableException hmnre){
        return CustomErrorPage.createBadRequestErrorPage();
    }

    //exception for incompatible types for the database
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity DataIntegrityViolationExceptionCatcher(HttpMessageNotReadableException hmnre){
        return CustomErrorPage.createBadRequestErrorPage();
    }
}
