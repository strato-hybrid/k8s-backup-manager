package kr.co.strato.migrationcore.core.restore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper=true)
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestoreProgress {
    private int itemsRestored;
    private int totalItems;
}
