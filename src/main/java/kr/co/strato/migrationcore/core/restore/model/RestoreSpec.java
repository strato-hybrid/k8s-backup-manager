package kr.co.strato.migrationcore.core.restore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import kr.co.strato.migrationcore.gloabal.common.model.MigrationCommonSpec;
import kr.co.strato.migrationcore.gloabal.common.model.MigrationDtoCommonSpec;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper=true)
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestoreSpec extends MigrationCommonSpec {
    private String backupName;
    private String scheduleName;
    private Boolean restorePVs;             // default : true
    private Boolean preserveNodePorts;      // default : true
}
