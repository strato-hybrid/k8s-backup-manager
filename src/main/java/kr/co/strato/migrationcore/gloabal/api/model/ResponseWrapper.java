package kr.co.strato.migrationcore.gloabal.api.model;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class ResponseWrapper<T> implements Serializable {
    private ResponseMessage message;
    private T result;

    public ResponseWrapper(T result){
        this.message = ResponseMessage.builder().build();
        this.result = result;
    }

    public ResponseWrapper(T result, ResponseMessage message){
        this.message = message;
        this.result = result;
    }

    public ResponseWrapper(ResponseMessage message){
        this.message = message;
    }
}
