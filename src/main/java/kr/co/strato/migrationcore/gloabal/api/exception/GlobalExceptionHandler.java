package kr.co.strato.migrationcore.gloabal.api.exception;

import kr.co.strato.migrationcore.gloabal.api.model.ResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    protected ResponseWrapper<String> handleException(Exception e) {
        log.error("Exception", e);

        MigrationException migrationException = new MigrationException(e);
        return new ResponseWrapper<>(migrationException.getResponseMessage());

    }

    @ExceptionHandler(value = {MigrationException.class})
    protected ResponseWrapper<String> handleGeneralException(MigrationException migrationException) {
        log.error("Exception",migrationException);
        return new ResponseWrapper<>(migrationException.getResponseMessage());
    }
}
