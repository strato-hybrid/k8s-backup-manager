package kr.co.strato.migrationcore.gloabal.common.util;

public enum WorkType {
    BACKUP("Backup"),
    RESTORE("Restore"),
    MIGRATION("Migration");


    String workType;

    WorkType(String workType) {this.workType = workType;}

    public String getValue() {
        return workType;
    }

    public static WorkType getType(String value){
        return WorkType.valueOf(value.toUpperCase());
    }
}
