package kr.co.strato.migrationcore.gloabal.common.util;

public enum WorkActionType {
    INCLUDE("Include"),
    EXCLUDE("Exclued");

    String workActionType;

    WorkActionType(String workActionType) {this.workActionType = workActionType;}

    public String getValue() {
        return workActionType;
    }

    public static WorkActionType getType(String value){
        return WorkActionType.valueOf(value.toUpperCase());
    }
}
