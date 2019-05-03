package br.com.io.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Boolean businessException(BusinessException e) {
        LOG.debug(e.getMessage(), e);
        return false;
    }
    
//    @ExceptionHandler(BadHttpRequest.class)
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    public @ResponseBody JsonResponseMessage badRequest(BadHttpRequest e) {
//        LOG.debug(e.getMessage(), e);
//        return new JsonResponseMessage(e.getMessage() != null ? e.getMessage() : "Bad Request!");
//    }
}