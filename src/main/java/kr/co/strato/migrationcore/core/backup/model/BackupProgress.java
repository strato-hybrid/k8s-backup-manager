package kr.co.strato.migrationcore.core.backup.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper=true)
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BackupProgress {
    private int itemsBackedUp;
    private int totalItems;
}
