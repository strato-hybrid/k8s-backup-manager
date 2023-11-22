package kr.co.strato.migrationcore.core.migration.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.strato.migrationcore.core.backup.model.BackupRequestDto;
import kr.co.strato.migrationcore.core.backup.model.BackupStatus;
import kr.co.strato.migrationcore.core.backup.service.BackupService;
import kr.co.strato.migrationcore.core.migration.model.MigrationRequestDto;
import kr.co.strato.migrationcore.core.restore.model.RestoreRequestDto;
import kr.co.strato.migrationcore.core.restore.model.RestoreStatus;
import kr.co.strato.migrationcore.core.restore.service.RestoreService;
import kr.co.strato.migrationcore.domain.migration.entity.MigrationHistory;
import kr.co.strato.migrationcore.domain.migration.service.MigrationHistoryDomainService;
import kr.co.strato.migrationcore.domain.resource.entity.MigrationResourceType;
import kr.co.strato.migrationcore.domain.resource.service.MigrationResourceTypeDomainService;
import kr.co.strato.migrationcore.gloabal.api.exception.MigrationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class MigrationService {
    @Autowired
    MigrationHistoryDomainService migrationHistoryDomainService;

    @Autowired
    MigrationResourceTypeDomainService migrationResourceTypeDomainService;

    @Autowired
    BackupService backupService;

    @Autowired
    RestoreService restoreService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public void execute(MigrationRequestDto migrationRequestDto) {
        MigrationHistory migrationHistory = new MigrationHistory();
        BackupRequestDto backupRequestDto = new BackupRequestDto();
        RestoreRequestDto restoreRequestDto = new RestoreRequestDto();

        try {
            setRequest(migrationRequestDto, backupRequestDto, restoreRequestDto);
            setMigrationHistorySpec(migrationRequestDto, migrationHistory);

            // insert
            migrationHistoryDomainService.register(migrationHistory);

            // Backup 실행
            Long backupIdx = backupService.execute(backupRequestDto);

            // update BackupIdx
            migrationHistory.setBackupIdx(backupIdx);
            migrationHistoryDomainService.updateMigrationHistory(migrationHistory);

            // Restore Cluster쪽에서 Velero Backup을 확인할 수 있을 때 까지 대기
            backupService.waitFindBackup(restoreRequestDto.getRestoreClusterIdx(), restoreRequestDto.getBackupName());

            // Restore 실행
            Long restoreIdx =restoreService.execute(restoreRequestDto);

            // update restoreIdx
            migrationHistory.setRestoreIdx(restoreIdx);
            migrationHistoryDomainService.updateMigrationHistory(migrationHistory);
        } catch(MigrationException e) {
            throw e;
        } catch(Exception e) {

            throw new MigrationException("3001");
        }
    }

    public Map<String, Object> checkMigrationStatus(String resourceName) {
        return null;
    }

    private void setRequest(MigrationRequestDto migrationRequestDto, BackupRequestDto backupRequestDto, RestoreRequestDto restoreRequestDto){
        String backupName = migrationRequestDto.getName()+"-backup";
        backupRequestDto.setName(backupName);
        backupRequestDto.setBackupClusterIdx(migrationRequestDto.getBackupClusterIdx());
        backupRequestDto.setTtl(migrationRequestDto.getTtl());

        backupRequestDto.setCsiSnapshotTimeout(migrationRequestDto.getCsiSnapshotTimeout());
        backupRequestDto.setItemOperationTimeout(migrationRequestDto.getItemOperationTimeout());
        backupRequestDto.setIncludedNamespaces(migrationRequestDto.getIncludedNamespaces());
//        backupRequestDto.setExcludedNamespaces(migrationRequestDto.getExcludedNamespaces());

        restoreRequestDto.setName(migrationRequestDto.getName()+"-restore");
        restoreRequestDto.setRestoreClusterIdx(migrationRequestDto.getRestoreClusterIdx());
        restoreRequestDto.setBackupName(backupName);
        restoreRequestDto.setScheduleName(migrationRequestDto.getScheduleName());
        restoreRequestDto.setRestorePVs(migrationRequestDto.getRestorePVs());
        restoreRequestDto.setPreserveNodePorts(migrationRequestDto.getPreserveNodePorts());

        restoreRequestDto.setCsiSnapshotTimeout(migrationRequestDto.getCsiSnapshotTimeout());
        restoreRequestDto.setItemOperationTimeout(migrationRequestDto.getItemOperationTimeout());
        restoreRequestDto.setIncludedNamespaces(migrationRequestDto.getIncludedNamespaces());
    }

    private void setMigrationHistorySpec(MigrationRequestDto migrationRequestDto, MigrationHistory migrationHistory) {
        Map<String, Object> specMap = MAPPER.convertValue(migrationRequestDto,Map.class);

        // 불필요한 항목 제거
        specMap.remove("name");
        specMap.remove("backupClusterIdx");
        specMap.remove("restoreClusterIdx");
        migrationHistory.setSpec(specMap);
    }

    public List<MigrationResourceType> getMigrationResourceTypeList() {
        try {
            List<MigrationResourceType> migrationResourceTypes = migrationResourceTypeDomainService.getMigrationResourceTypeList();

            return migrationResourceTypes;
        } catch (Exception e) {
            throw new MigrationException("3002");
        }
    }
}
