package com.pkalinov.autopartsmgmtserver.controllers;

import com.pkalinov.autopartsmgmtserver.CustomErrorPage;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class UnhandledExceptionController implements ErrorController {

    @RequestMapping(value = "/error")
    public ResponseEntity errorCode(HttpServletRequest request) {
        Object statusError = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (statusError != null) {
            int statusCode = Integer.parseInt(statusError.toString());
            HttpStatus httpStatus = HttpStatus.resolve(statusCode);
            if (httpStatus != null) {
                return CustomErrorPage.createErrorPage(statusCode, httpStatus.getReasonPhrase());
            }
        }

        return CustomErrorPage.createBadRequestErrorPage();
    }
}
