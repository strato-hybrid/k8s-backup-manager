package kr.co.strato.migrationcore.gloabal.api.exception;

import kr.co.strato.migrationcore.gloabal.api.model.ResponseMessage;
import kr.co.strato.migrationcore.gloabal.api.model.ResponseMessageProvider;
import lombok.Getter;

@Getter
public class MigrationException extends RuntimeException {
	ResponseMessage responseMessage;

    public MigrationException() {
    	this(ResponseMessageProvider.getResponseMessage("99999"));
    }

    public MigrationException(ResponseMessage responseMessage) {
    	this.responseMessage = responseMessage;
    }

    public MigrationException(String code) {
        this.responseMessage = ResponseMessageProvider.getResponseMessage(code);
    }

    public MigrationException(Throwable cause){
        super(cause);
        this.responseMessage = ResponseMessageProvider.getResponseMessageWithDetail("99999",cause.getMessage());
    }
}
