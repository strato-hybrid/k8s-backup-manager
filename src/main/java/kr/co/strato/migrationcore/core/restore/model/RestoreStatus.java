package kr.co.strato.migrationcore.core.restore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import kr.co.strato.migrationcore.gloabal.common.model.MigrationCommonStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestoreStatus extends MigrationCommonStatus {
    private RestoreProgress progress;
}
