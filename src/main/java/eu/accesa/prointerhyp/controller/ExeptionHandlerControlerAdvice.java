package eu.accesa.prointerhyp.controller;

import eu.accesa.prointerhyp.exeptions.EntityNotFoundException;
import eu.accesa.prointerhyp.exeptions.ProInterhypExeptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExeptionHandlerControlerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ExeptionHandlerControlerAdvice.class);

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public String handleEntityNotFoundException(EntityNotFoundException exception) {
        logger.warn(exception.getMessage());
        return exception.getMessage();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ProInterhypExeptions.class)
    public String handleProInterhypException(ProInterhypExeptions proInterhypExeptions) {
        logger.warn(proInterhypExeptions.getMessage());
        return proInterhypExeptions.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception exception) {
        logger.warn(exception.getMessage(), exception);
        return exception.getMessage();
    }
}
