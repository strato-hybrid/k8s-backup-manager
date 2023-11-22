package kr.co.strato.migrationcore.gloabal.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ResponseMessage {
    private	String code;
    private	String message;
    private String detail;
}
