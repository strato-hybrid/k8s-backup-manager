package kr.co.strato.migrationcore.gloabal.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.fabric8.kubernetes.api.model.KubernetesResource;
import io.fabric8.kubernetes.api.model.LabelSelector;
import io.fabric8.kubernetes.api.model.runtime.RawExtension;
import lombok.*;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class MigrationCommonSpec implements KubernetesResource {
    private String csiSnapshotTimeout;
    private String itemOperationTimeout;                // 최대 작업 대기 시간 (default : "1h")
    private List<String> includedNamespaces;
    private List<String> excludedNamespaces;
    private List<String> includedResources;
    private List<String> excludedResources;
    private LabelSelector labelSelector;
}
