package kr.co.strato.migrationcore.gloabal.common.model;


import io.fabric8.kubernetes.api.model.LabelSelector;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public abstract class MigrationDtoCommonSpec {
    private String csiSnapshotTimeout;
    private String itemOperationTimeout;                // 최대 작업 대기 시간 (default : "1h")
    private List<String> includedNamespaces;
    private List<String> excludedNamespaces;
    private List<String> includedResources;
    private List<String> excludedResources;
    private LabelSelector labelSelector;
}
