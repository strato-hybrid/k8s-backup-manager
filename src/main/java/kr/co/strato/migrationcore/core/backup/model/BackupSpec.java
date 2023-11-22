package kr.co.strato.migrationcore.core.backup.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import kr.co.strato.migrationcore.gloabal.common.model.MigrationCommonSpec;
import kr.co.strato.migrationcore.gloabal.common.model.MigrationDtoCommonSpec;
import lombok.*;

@ToString(callSuper=true)
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BackupSpec extends MigrationCommonSpec {
    private String ttl;               // 백업 유지 시간
}
