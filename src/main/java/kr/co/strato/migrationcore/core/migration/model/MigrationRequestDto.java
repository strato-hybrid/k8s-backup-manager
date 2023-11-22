package kr.co.strato.migrationcore.core.migration.model;

import io.fabric8.kubernetes.api.model.LabelSelector;
import kr.co.strato.migrationcore.core.backup.model.BackupSpec;
import kr.co.strato.migrationcore.core.restore.model.RestoreSpec;
import kr.co.strato.migrationcore.gloabal.common.model.MigrationDtoCommonSpec;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
public class MigrationRequestDto extends MigrationDtoCommonSpec {
    @NotNull
    private String name;

    @NotNull
    private Long backupClusterIdx;

    @NotNull
    private Long restoreClusterIdx;

    // Backup Spec
    private final String ttl = "24h0m0s";               // 백업 유지 시간

    // Restore Spec
    private String backupName;
    private String scheduleName;
    private Boolean restorePVs;             // default : true
    private Boolean preserveNodePorts;      // default : true
}
