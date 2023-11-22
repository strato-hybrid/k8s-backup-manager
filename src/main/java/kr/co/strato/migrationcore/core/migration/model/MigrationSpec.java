package kr.co.strato.migrationcore.core.migration.model;


import kr.co.strato.migrationcore.gloabal.common.model.MigrationCommonSpec;
import kr.co.strato.migrationcore.gloabal.common.model.MigrationDtoCommonSpec;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class MigrationSpec extends MigrationCommonSpec {
    // Backup
    private final String ttl = "24h0m0s";               // 백업 유지 시간

    // Restore
    private String backupName;
    private String scheduleName;
    private Boolean restorePVs;             // default : true
    private Boolean preserveNodePorts;      // default : true
}
