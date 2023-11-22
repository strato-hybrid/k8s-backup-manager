package kr.co.strato.migrationcore.gloabal.common.model;

import io.fabric8.kubernetes.api.model.ObjectMeta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ResourceCommon {
    private String apiVersion;
    private ObjectMeta metadata;
    private String Kind;
}
