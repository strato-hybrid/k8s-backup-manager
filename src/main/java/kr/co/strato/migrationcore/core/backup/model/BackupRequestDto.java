package kr.co.strato.migrationcore.core.backup.model;


import io.fabric8.kubernetes.api.model.LabelSelector;
import kr.co.strato.migrationcore.gloabal.common.model.MigrationCommonSpec;
import kr.co.strato.migrationcore.gloabal.common.model.MigrationDtoCommonSpec;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
public class BackupRequestDto extends MigrationDtoCommonSpec {
    @NotNull
    private String name;

    @NotNull
    private Long backupClusterIdx;

    private String description;

    // BackupSpec
    private String ttl;               // 백업 유지 시간
}
