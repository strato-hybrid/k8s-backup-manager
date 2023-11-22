package kr.co.strato.migrationcore.gloabal.common.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.fabric8.kubernetes.api.model.KubernetesResource;
import io.fabric8.kubernetes.api.model.runtime.RawExtension;
import kr.co.strato.migrationcore.gloabal.common.util.PhaseType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
public abstract class MigrationCommonStatus extends RawExtension {
    private String phase;
    private String completionTimestamp;
    private String startTimestamp;
}
