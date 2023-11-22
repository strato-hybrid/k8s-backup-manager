package kr.co.strato.migrationcore.gloabal.common.util;

public enum PhaseType {
    // Velero에서 사용되는 Phase
    NEW("New"),
    FAILED_VALIDATION("FailedValidation"),
    IN_PROGRESS("InProgress"),
    WAITING_FOR_PLUGIN_OPERATIONS("WaitingForPluginOperations"),
    FINALIZINGAFTER_PLUGIN_OPERATIONS("FinalizingafterPluginOperations"),
    FINALIZING_PARTIALLY_FAILED("FinalizingPartiallyFailed"),
    COMPLETED("Completed"),
    PARTIALLY_FAILED("PartiallyFailed"),
    FAILED("Failed"),

    // DB 상태 표기를 위해 추가된 Phase
    START("Start"),
    ERROR("Error");

    String progressType;

    PhaseType(String progressType) {this.progressType = progressType;}

    public String getValue() {
        return progressType;
    }

    public static PhaseType getType(String value){
        return PhaseType.valueOf(value.toUpperCase());
    }
}
