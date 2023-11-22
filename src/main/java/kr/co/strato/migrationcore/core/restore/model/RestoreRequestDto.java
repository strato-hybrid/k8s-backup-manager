package kr.co.strato.migrationcore.core.restore.model;

import io.fabric8.kubernetes.api.model.LabelSelector;
import kr.co.strato.migrationcore.gloabal.common.model.MigrationDtoCommonSpec;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
public class RestoreRequestDto extends MigrationDtoCommonSpec {
    @NotNull
    private String name;

    @NotNull
    private Long restoreClusterIdx;

    private String description;

    // Restore Spec
    @NotNull
    private String backupName;

    private String scheduleName;
    private Boolean restorePVs;             // default : true
    private Boolean preserveNodePorts;      // default : true
}
