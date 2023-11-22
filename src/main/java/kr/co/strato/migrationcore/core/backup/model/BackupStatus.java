package kr.co.strato.migrationcore.core.backup.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import kr.co.strato.migrationcore.gloabal.common.model.MigrationCommonStatus;
import lombok.*;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BackupStatus extends MigrationCommonStatus {
    private BackupProgress progress;
}
